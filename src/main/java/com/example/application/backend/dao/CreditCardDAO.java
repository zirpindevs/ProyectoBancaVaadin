package com.example.application.backend.dao;

import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.Transaction;

import java.util.List;

public interface CreditCardDAO {

    CreditCard findById(Long id);
}
