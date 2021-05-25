package com.example.application.backend.dao;

import com.example.application.backend.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionOperationsDao {

    List<Transaction> getDailyBalanceByDateRangeByNumAccount(Long idBankAccount, Map<String, String> map1);

    List getTotalTransactionsByDateRangeByNumAccount(Long idBankAccount, Map<String, String> map1);

    List<Transaction> getDailyTransactionByDateRangeByCreditCard(Long idCreditCard, Map<String, String> map1);

    List<Transaction> getAllOperationsByCategoryBankAccount(Long idBankAccount, Map<String, String> map1);

    List<Transaction> getAllOperationsByCategoryCreditCard(Long idCreditCard, Map<String, String> map1);



}
