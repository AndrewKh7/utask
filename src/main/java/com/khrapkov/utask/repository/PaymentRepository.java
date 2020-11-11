package com.khrapkov.utask.repository;

import com.khrapkov.utask.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query(" SELECT SUM(amount) FROM PaymentEntity WHERE sender = :senderName")
    Double getTotalAmountByPerson(String sender);
}
