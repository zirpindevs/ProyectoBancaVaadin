package com.example.application.backend.dao;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.transaction.operations.TransactionsBankAccountResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;

import java.util.List;
import java.util.Map;

public interface TransactionDAO {

    Transaction findById(Long id);

    TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1);

    TransactionsCreditcardResponse findAllTransactionsByDateRangeByIdCreditcard(Long idCreditcard, Map<String, String> map1);

    Boolean insertNewTransactionAndUpdateBalance(TransactionDTO transactionDTO, Double balance_after_transaction);

    Object findAllBalanceAfterTransaction(Long idUser);

    TransactionsBankAccountResponse findAllTransactionsByDateRangeByIdBankAccount(Long idBankaccount, Map<String, String> map1);



    }
