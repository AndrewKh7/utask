package com.khrapkov.utask.controller;

import com.khrapkov.utask.dto.request.RequestPaymentDto;
import com.khrapkov.utask.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService service){
        this.paymentService = service;
    }

    @PostMapping
    public void saveLisOfPayments(@RequestBody List<RequestPaymentDto> paymentDtos){
        this.paymentService.savePaymentList(paymentDtos);
    }

    @GetMapping()
    public Long getTotalAmountBySender(@RequestParam String name){
        return this.paymentService.getTotalAmountBySender(name);
    }
}
