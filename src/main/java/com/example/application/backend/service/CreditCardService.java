package com.example.application.backend.service;

import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.CreditCardDTO;

import java.util.List;

public interface CreditCardService {

    List<CreditCard> findAll();

    CreditCard findOne(Long id);

    List<CreditCard> findbyUser(Long id);

    CreditCard createCreditCard(CreditCardDTO creditCardDTO);

    CreditCard updateCreditCard(Long id, CreditCardDTO creditCardDTO);

    CreditCard markAsDeleteOne(Long id);

}
