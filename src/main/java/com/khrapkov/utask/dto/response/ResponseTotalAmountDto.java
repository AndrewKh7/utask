package com.khrapkov.utask.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseTotalAmountDto {
    private String sender;
    private BigDecimal totalAmount;
}
