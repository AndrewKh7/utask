package com.khrapkov.utask.service;

import com.khrapkov.utask.entity.PaymentEntity;
import com.khrapkov.utask.exceptions.NotFoundException;
import com.khrapkov.utask.repository.first_db_repository.FirstDBPaymentRepository;
import com.khrapkov.utask.repository.second_db_repository.SecondDBPaymentRepository;
import com.khrapkov.utask.repository.third_db_repository.ThirdDBPaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ShardingService {

    FirstDBPaymentRepository firstDBPaymentRepository;
    SecondDBPaymentRepository secondDBPaymentRepository;
    ThirdDBPaymentRepository thirdDBPaymentRepository;
    private AtomicInteger count;

    @Autowired
    public ShardingService(FirstDBPaymentRepository firstDBPaymentRepository,
                           SecondDBPaymentRepository secondDBPaymentRepository,
                           ThirdDBPaymentRepository thirdDBPaymentRepository) {
        this.firstDBPaymentRepository = firstDBPaymentRepository;
        this.secondDBPaymentRepository = secondDBPaymentRepository;
        this.thirdDBPaymentRepository = thirdDBPaymentRepository;
        this.count = new AtomicInteger(-1);
    }

    public List<PaymentEntity> saveAll(List<PaymentEntity> payments){
        List<List<PaymentEntity>> partition = new ArrayList<>();

        int divider = payments.size()/3;
            partition.add( new CopyOnWriteArrayList<>( payments.subList(0, divider)) );
            partition.add( new CopyOnWriteArrayList<>( payments.subList(divider, 2 * divider)));
            partition.add( new CopyOnWriteArrayList<>( payments.subList(2 * divider, 3 * divider)));

        for(int i = divider*3; i<payments.size();i++){
            int cnt = count.updateAndGet(val -> val>=2 ? 0: val+1 );
            List<PaymentEntity> temp = partition.get( cnt );
            temp.add(payments.get(i));
        }

        List<PaymentEntity> totalList = new ArrayList<>();
        totalList.addAll( firstDBPaymentRepository.saveAll(partition.get(0)) );
        totalList.addAll( secondDBPaymentRepository.saveAll(partition.get(1)) );
        totalList.addAll( thirdDBPaymentRepository.saveAll(partition.get(2)) );
        return totalList;
    }

    public BigDecimal getTotalAmountBySender(String sender){
        BigDecimal[] values = new BigDecimal[]{
                firstDBPaymentRepository.getTotalAmountBySender(sender),
                secondDBPaymentRepository.getTotalAmountBySender(sender),
                thirdDBPaymentRepository.getTotalAmountBySender(sender)
        };
        return Arrays.stream(values)
                .filter(v -> v != null)
                .reduce((f,s) -> f.add(s))
                .orElseThrow(() -> new NotFoundException("Sender not found") );
    };


}
