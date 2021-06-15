package com.example.application.backend.service;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.transaction.operations.TransactionsBankAccountResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.model.transaction.operations.idbankaccountTransactions.TransactionsByBankAccountResponse;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<Transaction> findAll();

    Transaction findOne(Long id);

    TransactionsBankAccountResponse findAllTransactionsByDateRangeByIdBankAccount(Long idBankAccount, Map<String, String> map1);

    TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1);

    Transaction createTransaction(TransactionDTO transactionDTO);

    Transaction updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Transaction transactionToDelete);


    TransactionsCreditcardResponse findAllTransactionsByDateRangeByIdCreditcard(Long idCreditcard, Map<String, String> map1);


    Transaction createTransactionForm(TransactionDTO transactionDTO);

    Boolean createTransactionVaadin(TransactionDTO transactionDTO);

    Object[] findAllBalanceAfterTransaction(Long idUser);


    }
