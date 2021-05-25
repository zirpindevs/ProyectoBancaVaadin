package com.example.application.backend.repository;

import com.example.application.backend.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findOneById(Long id);

    boolean existsById(Long id);

    Boolean existsByNumAccount(String numAccount);
}
