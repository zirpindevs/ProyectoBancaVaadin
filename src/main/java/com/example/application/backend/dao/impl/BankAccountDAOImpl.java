package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.BankAccountDAO;
import com.example.application.backend.model.*;
import com.example.application.backend.model.bankaccount.operations.BankAccountUserResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.sql.Timestamp;
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

    @Override
    public BankAccountUserResponse findAllBankAccountsByIdUser(Long idUser) {
/*
    select * from bank_accounts ba INNER JOIN users_bank_accounts uba on uba.bank_account_id = ba.id WHERE uba.user_id = 1
*/
        try {

            Query queryNative = manager.createNativeQuery(
                    "select * from bank_accounts ba INNER JOIN users_bank_accounts uba on uba.bank_account_id = ba.id WHERE"
                               + " uba.user_id = " + idUser
            );

            List resultDB = queryNative.getResultList();


            if (resultDB.size() == 0)
                return new BankAccountUserResponse("-204");


                BankAccountUserResponse result = mapoutToBankAccountByUserResponse(resultDB);

            return result;

        } catch (Exception e) {
            log.error(e.getMessage());
            return new BankAccountUserResponse("-500");

        }
    }


    private BankAccountUserResponse mapoutToBankAccountByUserResponse(List resultDB){

        BankAccountUserResponse result = new BankAccountUserResponse();

        resultDB.forEach(
                item -> {
                    BankAccount bankAccount = new BankAccount();

                    BigInteger idBankAccount = (BigInteger) ((Object[]) item)[0];

                    bankAccount.setId( idBankAccount.longValue() );

                    bankAccount.setBalance( (Double) ((Object[]) item)[1] );

                    Timestamp createdDate = (Timestamp) ((Object[]) item)[2];
                    bankAccount.setCreatedAt(createdDate.toLocalDateTime());

                    bankAccount.setDeleted( (Boolean) ((Object[]) item)[3] );
                    bankAccount.setEnabled( (Boolean) ((Object[]) item)[4] );

                    if (((Object[]) item)[5] != null)
                        bankAccount.setNumAccount( (String) ((Object[]) item)[5] );

                    result.getBankAccounts().add(bankAccount);

                }
        );


        result.setStatus("200");

        return result;

    }
}
