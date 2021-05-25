package com.example.application.backend.repository;

import com.example.application.backend.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findOneById(Long id);

    boolean existsById(Long id);

    Boolean existsByNumCreditCard(String numCreditCard);

}
