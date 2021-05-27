package com.example.application.backend.dao;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.bankaccount.operations.BankAccountUserResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;

import java.util.List;
import java.util.Map;

public interface BankAccountDAO {
    List<BankAccount> findAllByFilters(Map<String, String> map1);

    BankAccount findById(Long id);

    BankAccountUserResponse findAllBankAccountsByIdUser(Long idUser);
}
