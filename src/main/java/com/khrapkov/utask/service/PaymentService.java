package com.khrapkov.utask.service;

import com.khrapkov.utask.dto.request.RequestPaymentDto;
import com.khrapkov.utask.dto.response.ResponseTotalAmountDto;
import com.khrapkov.utask.entity.PaymentEntity;
import com.khrapkov.utask.exceptions.NotFoundException;
import com.khrapkov.utask.repository.PaymentRepository;
import com.khrapkov.utask.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final Mapper mapper;

    @Autowired
    public PaymentService(PaymentRepository repository, Mapper mapper){
        this.paymentRepository = repository;
        this.mapper = mapper;
    }

    public void savePaymentList(List<RequestPaymentDto> paymentDtos){
        List<PaymentEntity> payments = paymentDtos.stream()
                .map(m -> mapper.toPaymentEntity(m))
                .collect(Collectors.toList());
        this.paymentRepository.saveAll(payments);
    }

    public ResponseTotalAmountDto getTotalAmountBySender(String sender){
        Long totalAmount = this.paymentRepository.getTotalAmountByPerson(sender);
        if(totalAmount == null)
            throw new NotFoundException("The sender with name  \"" + sender + "\" was not found");
        ResponseTotalAmountDto totalAmountDto = new ResponseTotalAmountDto();
        totalAmountDto.setSender(sender);
        totalAmountDto.setTotalAmount(totalAmount);
        return totalAmountDto;
    }
}
