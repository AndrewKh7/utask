package com.khrapkov.utask.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Data
@Table(name = "payment", indexes = { @Index(name = "SENDER_IDX", columnList = "sender") })
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sender;
    private String recipient;
    private BigDecimal amount;
}
