package com.khrapkov.utask.dto.request;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class RequestPaymentDto {
    private String sender;
    private String recipient;
    private BigDecimal amount;
}
