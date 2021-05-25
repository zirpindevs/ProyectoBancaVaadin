package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.BankAccountDAO;
import com.example.application.backend.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BankAccountDAOImpl implements BankAccountDAO {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(BankAccountDAOImpl.class);

    /**
     * Dao Method:
     * Get all Bank account with optionals pagination filters (String pag, String limit)
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of bank accounts from database
     */
    @Override
    public List<BankAccount> findAllByFilters(Map<String, String> map1) {
        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<BankAccount> criteria = builder.createQuery(BankAccount.class);
            Root<BankAccount> root = criteria.from(BankAccount.class);

            criteria.distinct(true).select(root);

            TypedQuery<BankAccount> banksAccountsQuery = manager.createQuery(criteria);

            if(map1.get("page")!=null && map1.get("limit")!=null){
                banksAccountsQuery.setFirstResult(Integer.parseInt(map1.get("page")));
                banksAccountsQuery.setMaxResults(Integer.parseInt(map1.get("limit")));
            }

            return banksAccountsQuery.getResultList();

        }catch (Exception e){

            log.error(e.getMessage());
            List<BankAccount> bankAccountsError = new ArrayList<>();
            BankAccount bankAccountError = new BankAccount();
            bankAccountError.setId(-500L);
            bankAccountsError.add(bankAccountError);

            return bankAccountsError;
        }
    }

    @Override
    public BankAccount findById(Long id){

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<BankAccount> criteria =  builder.createQuery(BankAccount.class);
        Root<BankAccount> root = criteria.from(BankAccount.class);
        BankAccount bankAccount;

        criteria.select(root).where(builder.equal(root.get("id"), id));
        try{
            bankAccount = manager.createQuery(criteria).getSingleResult();
        }catch(Exception e){
            bankAccount = null;
        }

        return bankAccount;
    }

}
