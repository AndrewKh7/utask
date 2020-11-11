package com.khrapkov.utask.controller;

import com.khrapkov.utask.dto.request.RequestPaymentDto;
import com.khrapkov.utask.dto.response.ResponseTotalAmountDto;
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
    public void saveLisOfPayments(@RequestBody List<RequestPaymentDto> paymentsDto){
        this.paymentService.savePaymentList(paymentsDto);
    }

    @GetMapping()
    public ResponseTotalAmountDto getTotalAmountBySender(@RequestParam String name){
        return this.paymentService.getTotalAmountBySender(name);
    }
}
