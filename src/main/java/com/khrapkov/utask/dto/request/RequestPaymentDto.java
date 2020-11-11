package com.khrapkov.utask.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class RequestPaymentDto {
    private String sender;
    private String recipient;
    private long amount;
}
