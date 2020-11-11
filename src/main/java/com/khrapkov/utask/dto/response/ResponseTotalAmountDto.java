package com.khrapkov.utask.dto.response;

import lombok.Data;

@Data
public class ResponseTotalAmountDto {
    private String sender;
    private Long totalAmount;
}
