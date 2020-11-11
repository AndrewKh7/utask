package com.khrapkov.utask.service;

import com.khrapkov.utask.dto.request.RequestPaymentDto;
import com.khrapkov.utask.dto.response.ResponseTotalAmountDto;
import com.khrapkov.utask.entity.PaymentEntity;
import com.khrapkov.utask.exceptions.NotFoundException;
import com.khrapkov.utask.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentService {

    ShardingService shardingService;
    private final Mapper mapper;

    public void savePaymentList(List<RequestPaymentDto> paymentDtos){
        List<PaymentEntity> payments = paymentDtos.stream()
                .map(m -> mapper.toPaymentEntity(m))
                .collect(Collectors.toList());
        this.shardingService.saveAll(payments);
    }

    public ResponseTotalAmountDto getTotalAmountBySender(String sender){
        Long totalAmount = this.shardingService.getTotalAmountBySender(sender);
        if(totalAmount == null)
            throw new NotFoundException("The sender with name  \"" + sender + "\" was not found");
        ResponseTotalAmountDto totalAmountDto = new ResponseTotalAmountDto();
        totalAmountDto.setSender(sender);
        totalAmountDto.setTotalAmount(totalAmount);
        return totalAmountDto;
    }
}
