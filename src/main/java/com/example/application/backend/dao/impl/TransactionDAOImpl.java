package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.model.MovimientoType;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.transaction.operations.DailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(TransactionDAOImpl.class);

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

    @Override
    public TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1) {

        try {

            Query queryNative = manager.createNativeQuery(
                    "SELECT t.id, t.importe, t.balance_after_transaction, t.concepto, t.tipo_movimiento, t.created_date, t.last_modified, t.id_bank_account, t.id_credit_card, t.id_category " +
                            "FROM transactions t " +
                            "INNER JOIN users_bank_accounts uba " +
                            "ON t.id_bank_account = uba.bank_account_id " +
                            "WHERE t.created_date BETWEEN '"
                            + map1.get("startDate") + "'"
                            + " AND '" + map1.get("endDate") + "'"
                            + " AND uba.user_id = " + idUser
            );

            List resultDB = queryNative.getResultList();

            if (resultDB.size() == 0)
                return new TransactionsUserResponse("-204");

            TransactionsUserResponse result = mapoutToTransactionsUserResponse(resultDB, idUser, map1);


            return result;


        }catch (Exception e){
            log.error(e.getMessage());
            return new TransactionsUserResponse("-500");
        }


    }

    private TransactionsUserResponse mapoutToTransactionsUserResponse(List resultDB, Long idUser, Map<String, String> map1){

        TransactionsUserResponse result = new TransactionsUserResponse();

        resultDB.forEach(
            item -> {
                TransactionDTO transaction = new TransactionDTO();

                BigInteger idTransaction = (BigInteger) ((Object[]) item)[0];

                transaction.setId( idTransaction.longValue() );

                transaction.setImporte( (Double) ((Object[]) item)[1] );

                transaction.setBalanceAfterTransaction( (Double) ((Object[]) item)[2] );

                transaction.setConcepto( (String) ((Object[]) item)[3].toString() );

                String tipoMovimiento = (String) ((Object[]) item)[4].toString();

                transaction.setTipoMovimiento( MovimientoType.valueOf(tipoMovimiento) );

                Timestamp createdDate  = (Timestamp) ( (Object[]) item)[5];
                transaction.setCreatedDate( createdDate.toInstant() );

                if (((Object[]) item)[6] != null) {
                    Timestamp lasModified  = (Timestamp) ( (Object[]) item)[6];
                    transaction.setLastModified(lasModified.toInstant());
                }


                BigInteger idBankAccount = (BigInteger) ((Object[]) item)[7];

                transaction.setIdBankAccount(idBankAccount.longValue());

                if (((Object[]) item)[8] != null) {
                    BigInteger idCreditCard = (BigInteger) ((Object[]) item)[8];
                    transaction.setIdCreditCard(idCreditCard.longValue());
                }


                BigInteger idCategory = (BigInteger) ((Object[]) item)[9];
                transaction.setIdCategory(idCategory.longValue());


// t.last_modified, t.id_bank_account, t.id_credit_card, t.id_category
                result.getTransactions().add(transaction);
            }
        );



        result.setStatus("200");
        result.setIdUser(idUser);
        result.setStartDate(map1.get("startDate"));
        result.setEndDate(map1.get("endDate"));


        return result;

    }




















































































































    /**************************************************************************************************/


/*    @Override
    public List<Transaction> findTransactionByCreditCardId(Long id, Map<String, String> map1){
*//*
        SELECT c.*, t.* from credit_cards c INNER JOIN transactions t on c.id = t.id WHERE c.created_at >now() - interval 1 month AND c.id_user = 1
*//*
        try {
            Query queryNative = manager.createNativeQuery(
                    "SELECT * FROM transactions" +
                            " WHERE created_date >now() - interval 1 YEAR"
                            + " AND id_credit_card = " + id
            );
            List result = queryNative.getResultList();


            return result;

        }catch (Exception e){
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }*/

    @Override
    public TransactionsCreditcardResponse findAllTransactionsByDateRangeByIdCreditcard(Long idCreditcard, Map<String, String> map1) {

        try {

            Query queryNative = manager.createNativeQuery(
                    "SELECT * " +
                            "FROM transactions " +
                            "WHERE created_date BETWEEN '"
                            + map1.get("startDate") + "'"
                            + " AND '" + map1.get("endDate") + "'"
                            + " AND id_credit_card = " + idCreditcard
            );

            List resultDB = queryNative.getResultList();

            if (resultDB.size() == 0)
                return new TransactionsCreditcardResponse("-204");

            TransactionsCreditcardResponse result = mapoutToTransactionsCreditcardResponse(resultDB, idCreditcard, map1);


            return result;


        }catch (Exception e){
            log.error(e.getMessage());
            return new TransactionsCreditcardResponse("-500");
        }


    }

    private TransactionsCreditcardResponse mapoutToTransactionsCreditcardResponse(List resultDB, Long idCreditcard, Map<String, String> map1){

        TransactionsCreditcardResponse result = new TransactionsCreditcardResponse();

        resultDB.forEach(
                item -> {
                    TransactionDTO transaction = new TransactionDTO();

                    BigInteger idTransaction = (BigInteger) ((Object[]) item)[0];

                    transaction.setId( idTransaction.longValue() );

                    transaction.setBalanceAfterTransaction( (Double) ((Object[]) item)[1] );

                    transaction.setConcepto( (String) ((Object[]) item)[2].toString() );

                    Timestamp createdDate = (Timestamp) ((Object[]) item)[3];
                    transaction.setCreatedDate(createdDate.toInstant());

                    transaction.setImporte( (Double) ((Object[]) item)[4] );

                    if (((Object[]) item)[5] != null) {
                        Timestamp lasModified = (Timestamp) ((Object[]) item)[5];
                        transaction.setLastModified(lasModified.toInstant());
                    }

                    String tipoMovimiento = (String) ((Object[]) item)[6].toString();
                    transaction.setTipoMovimiento( MovimientoType.valueOf(tipoMovimiento) );


                    BigInteger idBankAccount = (BigInteger) ((Object[]) item)[7];
                    transaction.setIdBankAccount(idBankAccount.longValue());

                    BigInteger idCategory = (BigInteger) ((Object[]) item)[9];
                    transaction.setIdCategory(idCategory.longValue());

                    if (((Object[]) item)[9] != null) {
                        BigInteger idCreditCard = (BigInteger) ((Object[]) item)[9];
                        transaction.setIdCreditCard(idCreditCard.longValue());
                    }




// t.last_modified, t.id_bank_account, t.id_credit_card, t.id_category
                    result.getTransactions().add(transaction);
                }
        );



        result.setStatus("200");
        result.setIdUser(idCreditcard);
        result.setStartDate(map1.get("startDate"));
        result.setEndDate(map1.get("endDate"));


        return result;

    }

}