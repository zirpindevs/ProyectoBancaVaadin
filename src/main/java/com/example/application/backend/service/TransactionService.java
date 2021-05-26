package com.example.application.backend.service;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.model.transaction.operations.idbankaccountTransactions.TransactionsByBankAccountResponse;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<Transaction> findAll();

    Transaction findOne(Long id);

    TransactionsByBankAccountResponse findAllTransactionsByDateRangeByIdBankAccount(Long idBankAccount, Map<String, String> map1);

    TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1);

    Transaction createTransaction(TransactionDTO transactionDTO);

    Transaction updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Transaction transactionToDelete);

}
