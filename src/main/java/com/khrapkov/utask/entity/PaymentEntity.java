package com.khrapkov.utask.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sender;
    private String recipient;
    private Double amount;
}
