package com.khrapkov.utask.repository.first_db_repository;

import com.khrapkov.utask.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FirstDBPaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query(" SELECT SUM(amount) FROM PaymentEntity WHERE sender = :sender")
    Long getTotalAmountByPerson(String sender);
}
