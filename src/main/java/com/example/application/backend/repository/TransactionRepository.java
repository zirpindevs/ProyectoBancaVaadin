package com.example.application.backend.repository;

import com.example.application.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByBankAccount(Long bankAccount);
    Optional<Transaction> findOneById(Long id);

    boolean existsById(Long id);

}
