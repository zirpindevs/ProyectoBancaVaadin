package com.example.application.backend.service;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.BankAccountDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BankAccountService {

    List<BankAccount> findAll(Map<String, String> map1);

    Optional<BankAccount> findOne(Long id);

    BankAccount createOne(BankAccountDTO bankAccountDTO);

    BankAccount updateOne(Long id, BankAccountDTO bankAccountDTO);

    Optional<Boolean> deleteOne(Long id);

}
