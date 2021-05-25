package com.example.application.backend.dao;

import com.example.application.backend.model.BankAccount;

import java.util.List;
import java.util.Map;

public interface BankAccountDAO {
    List<BankAccount> findAllByFilters(Map<String, String> map1);

    BankAccount findById(Long id);
}
