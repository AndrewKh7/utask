package com.khrapkov.utask.service;

import com.khrapkov.utask.entity.PaymentEntity;
import com.khrapkov.utask.repository.first_db_repository.FirstDBPaymentRepository;
import com.khrapkov.utask.repository.second_db_repository.SecondDBPaymentRepository;
import com.khrapkov.utask.repository.third_db_repository.ThirdDBPaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShardingService {
    FirstDBPaymentRepository firstDBPaymentRepository;
    SecondDBPaymentRepository secondDBPaymentRepository;
    ThirdDBPaymentRepository thirdDBPaymentRepository;

    public List<PaymentEntity> saveAll(List<PaymentEntity> payments){
        List<PaymentEntity> totalList = new ArrayList<>();
        List<List<PaymentEntity>> partition = new ArrayList<>();
        int divider = payments.size()/3;
        partition.add( payments.subList(0, divider));
        partition.add( payments.subList(divider,2*divider));
        partition.add( payments.subList(2*divider, payments.size()));

        totalList.addAll( this.firstDBPaymentRepository.saveAll(partition.get(0)) );
        totalList.addAll( this.secondDBPaymentRepository.saveAll(partition.get(1)) );
        totalList.addAll( this.thirdDBPaymentRepository.saveAll(partition.get(2)) );
        return totalList;
    }

    public Long getTotalAmountBySender(String sender){
        Long sum = 0L;
        sum += this.firstDBPaymentRepository.getTotalAmountBySender(sender);
        sum += this.secondDBPaymentRepository.getTotalAmountBySender(sender);
        sum += this.thirdDBPaymentRepository.getTotalAmountBySender(sender);
        return sum;
    };
}