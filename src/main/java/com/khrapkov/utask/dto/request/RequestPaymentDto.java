package com.khrapkov.utask.dto.request;

import lombok.Data;

@Data
public class RequestPaymentDto {
    private String sender;
    private String recipient;
    private Double amount;
}
