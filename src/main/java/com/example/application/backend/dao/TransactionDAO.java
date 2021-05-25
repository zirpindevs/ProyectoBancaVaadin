package com.example.application.backend.dao;

import com.example.application.backend.model.Transaction;

public interface TransactionDAO {
    Transaction findById(Long id);

}
