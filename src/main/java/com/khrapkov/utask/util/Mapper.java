package com.khrapkov.utask.util;

import com.khrapkov.utask.dto.request.RequestPaymentDto;
import com.khrapkov.utask.entity.PaymentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper mapper = new ModelMapper();

    public PaymentEntity toPaymentEntity(RequestPaymentDto paymentDto){
        return mapper.map(paymentDto, PaymentEntity.class);
    }

    public RequestPaymentDto toRequestPaymentDto(PaymentEntity payment){
        return mapper.map(payment, RequestPaymentDto.class);
    }

}
