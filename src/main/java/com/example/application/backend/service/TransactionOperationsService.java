package com.example.application.backend.service;

import com.example.application.backend.model.transaction.operations.DailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.UserDailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.totalOperations.DailyOperationsResponse;
import com.example.application.backend.model.transaction.operations.totalTransactions.DailyTransactionResponse;

import java.util.List;
import java.util.Map;

public interface TransactionOperationsService {

    DailyBalanceResponse getDailyBalanceByDateRangeByNumAccount(Long id, Map<String, String> map1);

    UserDailyBalanceResponse getDailyBalanceByDateRangeByUser(Long idUser, Map<String, String> map1);

    DailyTransactionResponse getDailyTransactionByDateRangeByCreditCard(Long idCreditCard, Map<String, String> map1);

    DailyOperationsResponse getAllOperationsByCategoryBankAccount(Long idBankAccount, Map<String, String> map1);
    DailyOperationsResponse getAllOperationsByCategoryCreditCard(Long idCreditCard, Map<String, String> map1);

    List getAllOperationsByCategoryBankAccount(Long idBankAccount);


    }
