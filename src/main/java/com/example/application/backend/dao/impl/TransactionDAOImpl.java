package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.model.*;
import com.example.application.backend.model.transaction.operations.TransactionsBankAccountResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.model.transaction.operations.idbankaccountTransactions.TransactionsByBankAccountResponse;
import com.example.application.backend.service.BankAccountService;
import com.example.application.backend.service.CreditCardService;
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
import java.util.List;
import java.util.Map;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    @PersistenceContext
    private EntityManager manager;


    private final Logger log = LoggerFactory.getLogger(TransactionDAOImpl.class);

    private final BankAccountService bankAccountService;

    private final CreditCardService creditCardService;

    public TransactionDAOImpl(BankAccountService bankAccountService, CreditCardService creditCardService) {
        this.bankAccountService = bankAccountService;
        this.creditCardService = creditCardService;
    }

    /**
     * Find a transaction by id in the transaction table
     *
     * @param id user to search in the database
     * @return Transaction
     */
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


    /**
     * Find all transactions in a data range of a custom id user
     *
     * @param idUser user to search in the database
     * @param map1 data range map
     * @return TransactionsUserResponse
     */
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

    /**
     * Function to map the response of a creditcard findAllTransactionsByDateRangeByIdUser query
     *
     * @param resultDB database result
     * @param idUser id to assign the map to a custom user
     * @param map1 data range map
     * @return TransactionsUserResponse
     */
    private TransactionsUserResponse mapoutToTransactionsUserResponse(List resultDB, Long idUser, Map<String, String> map1){

        TransactionsUserResponse result = new TransactionsUserResponse();

        resultDB.forEach(
            item -> {
                TransactionGrid transaction = new TransactionGrid();

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
                String numBankAccount = this.bankAccountService.findOne(idBankAccount.longValue()).get().getNumAccount();
                transaction.setNumBankAccount(numBankAccount);

                if (((Object[]) item)[8] != null) {
                    BigInteger idCreditCard = (BigInteger) ((Object[]) item)[8];
                    transaction.setIdCreditCard(idCreditCard.longValue());

                    String numCreditCard = this.creditCardService.findOne(idCreditCard.longValue()).getNumCreditCard();
                    transaction.setNumCreditCard(numCreditCard);
                }


                BigInteger idCategory = (BigInteger) ((Object[]) item)[9];
                transaction.setIdCategory(idCategory.longValue());


                result.getTransactions().add(transaction);
            }
        );


        result.setStatus("200");
        result.setIdUser(idUser);
        result.setStartDate(map1.get("startDate"));
        result.setEndDate(map1.get("endDate"));


        return result;

    }

    /**
     * Find all transactions in a data range of a custom bankaccount
     *
     * @param idBankaccount transaction info to create in bankaccount table
     * @param map1 data range map
     * @return Boolean if operations are succesfull
     */
    @Override
    public TransactionsBankAccountResponse findAllTransactionsByDateRangeByIdBankAccount(Long idBankaccount, Map<String, String> map1) {

        try {

            Query queryNative = manager.createNativeQuery(
                    "SELECT * " +
                            "FROM transactions " +
                            "WHERE created_date BETWEEN '"
                            + map1.get("startDate") + "'"
                            + " AND '" + map1.get("endDate") + "'"
                            + " AND id_bank_account = " + idBankaccount
            );

            List resultDB = queryNative.getResultList();

            if (resultDB.size() == 0)
                return new TransactionsBankAccountResponse("-204");

            TransactionsBankAccountResponse result = mapoutToTransactionsBankaccountResponse(resultDB, idBankaccount, map1);


            return result;


        }catch (Exception e){
            log.error(e.getMessage());
            return new TransactionsBankAccountResponse("-500");
        }


    }

    /**
     * Function to map the response of a bankaccount findAllTransactionsByDateRangeByidBankaccount query
     *
     * @param resultDB database result
     * @param idBankaccount id to assign the map to a custom Bankaccount
     * @param map1 data range map
     * @return TransactionsByBankAccountResponse
     */
    private TransactionsBankAccountResponse mapoutToTransactionsBankaccountResponse(List resultDB, Long idBankaccount, Map<String, String> map1){

        TransactionsBankAccountResponse result = new TransactionsBankAccountResponse();

        resultDB.forEach(
                item -> {
                    TransactionGrid transaction = new TransactionGrid();

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

                    if (((Object[]) item)[9] != null) {
                        BigInteger idCreditCard = (BigInteger) ((Object[]) item)[9];
                        transaction.setIdCreditCard(idCreditCard.longValue());
                    }


                    result.getTransactions().add(transaction);
                }
        );



        result.setStatus("200");
        result.setIdUser(idBankaccount);
        result.setStartDate(map1.get("startDate"));
        result.setEndDate(map1.get("endDate"));


        return result;

    }

    /**
     * Find all transactions in a data range of a custom creditcard
     *
     * @param idCreditcard transaction info to create in transaction table
     * @param map1 data range map
     * @return Boolean if operations are succesfull
     */
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


    /**
     * Function to map the response of a creditcard findAllTransactionsByDateRangeByIdCreditcard query
     *
     * @param resultDB database result
     * @param idCreditcard id to assign the map to a custom creditcard
     * @param map1 data range map
     * @return TransactionsCreditcardResponse
     */
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

                    if (((Object[]) item)[9] != null) {
                        BigInteger idCreditCard = (BigInteger) ((Object[]) item)[9];
                        transaction.setIdCreditCard(idCreditCard.longValue());
                    }


                    result.getTransactions().add(transaction);
                }
        );



        result.setStatus("200");
        result.setIdUser(idCreditcard);
        result.setStartDate(map1.get("startDate"));
        result.setEndDate(map1.get("endDate"));


        return result;

    }

    /**
     * Insert and update the balance of a transaction in transaction and bankaccount tables
     * (This operation could be made in a fast repository recover call, but makes problems with vaadin-hibernate threads
     * and this function bypass the problem)
     *
     * @param transactionDTO transaction info to create in transaction table
     * @param balance_after_transaction balante to update in the bankaccount table
     * @return Boolean if operations are succesfull
     */
    @Override
    public Boolean insertNewTransactionAndUpdateBalance(TransactionDTO transactionDTO, Double balance_after_transaction) {

        Timestamp date= Timestamp.from(Instant.now());

        TransactionDTO transactionError = new TransactionDTO();
        try {

            manager.createNativeQuery("INSERT INTO transactions (concepto, created_date, importe, tipo_movimiento, id_bank_account, balance_after_transaction, id_category) VALUES (?,?,?,?,?,?,?)")
                    .setParameter(1, transactionDTO.getConcepto())
                    .setParameter(2, date)
                    .setParameter(3, transactionDTO.getImporte())
                    .setParameter(4, transactionDTO.getTipoMovimiento().toString())
                    .setParameter(5, transactionDTO.getIdBankAccount())
                    .setParameter(6, balance_after_transaction)
                    .setParameter(7, transactionDTO.getIdCategory())


                    .executeUpdate();


             manager.createNativeQuery("UPDATE bank_accounts SET balance = " + balance_after_transaction +
                                        " WHERE id = "+ transactionDTO.getIdBankAccount())
                    .executeUpdate();


            return true;


        }catch (Exception e){
            log.error(e.getMessage());
            return false;

        }

    }

    /**
     * Recover the balance of a idUser from the database
     *
     * @param idUser transaction info to create in transaction table

     * @return Object[] required by Apex chart funtions
     */
    @Override
    public Object[] findAllBalanceAfterTransaction(Long idUser) {

        try {

            Query queryNative = manager.createNativeQuery(
                    "SELECT t.balance_after_transaction from transactions t " +
                            "INNER JOIN users_bank_accounts uba ON uba.bank_account_id = t.id_bank_account " +
                            "WHERE uba.user_id = " + idUser
            );

            List resultDB = queryNative.getResultList();

        return resultDB.toArray();

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }

    }

}