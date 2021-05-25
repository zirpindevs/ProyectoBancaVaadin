package com.example.application.backend.service;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO);
    Transaction updateTransaction(Long id, TransactionDTO transactionDTO);
    void deleteTransaction(Transaction transactionToDelete);

    Transaction findOne(Long id);
    List<Transaction> findAll();
}
