package com.example.application.backend.dao;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;

import java.util.Map;

public interface TransactionDAO {

    Transaction findById(Long id);

    TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1);
}
