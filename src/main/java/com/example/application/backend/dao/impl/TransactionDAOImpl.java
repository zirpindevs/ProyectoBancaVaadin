package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.model.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Transaction findById(Long id){

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria =  builder.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        Transaction transaction;

        criteria.select(root).where(builder.equal(root.get("id"), id));
        try{
            transaction = manager.createQuery(criteria).getSingleResult();
        }catch(Exception e){
            transaction = null;
        }

        return transaction;
    }
}
