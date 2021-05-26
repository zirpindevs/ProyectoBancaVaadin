package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.CreditCardDAO;
import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CreditCardDAOimpl implements CreditCardDAO {

    private final Logger log = LoggerFactory.getLogger(CreditCardDAOimpl.class);

    @PersistenceContext
    private EntityManager manager;

    @Override
    public CreditCard findById(Long id){

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteria =  builder.createQuery(CreditCard.class);
        Root<CreditCard> root = criteria.from(CreditCard.class);
        CreditCard creditCard;

        criteria.select(root).where(builder.equal(root.get("id"), id));
        try{
            creditCard = manager.createQuery(criteria).getSingleResult();
        }catch(Exception e){
            creditCard = null;
        }

        return creditCard;
    }

}
