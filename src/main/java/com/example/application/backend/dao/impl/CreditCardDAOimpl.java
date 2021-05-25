package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.CreditCardDAO;
import com.example.application.backend.model.CreditCard;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CreditCardDAOimpl implements CreditCardDAO {

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
