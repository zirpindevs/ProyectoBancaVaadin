package com.example.application.backend.dao;

import com.example.application.backend.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {

    List<User> findAllByFilters(Map<String, String> map1);

    List findAllBankAccountsByUser(Long idUser, Map<String, String> map1);

    public Optional<Boolean> deleteUsersBankAccountsRelation(Long idUser);

}
