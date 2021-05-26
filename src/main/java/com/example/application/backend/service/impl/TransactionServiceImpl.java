package com.example.application.backend.service.impl;

import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.model.*;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.model.transaction.operations.idbankaccountTransactions.TransactionsByBankAccountResponse;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.repository.CategoryRepository;
import com.example.application.backend.repository.CreditCardRepository;
import com.example.application.backend.repository.TransactionRepository;
import com.example.application.backend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final Logger log = LoggerFactory.getLogger(Transaction.class);

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final CreditCardRepository creditCardRepository;

    private final TransactionDAO transactionDAO;

    public TransactionServiceImpl(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository, CategoryRepository categoryRepository, CreditCardRepository creditCardRepository, TransactionDAO transactionDAO) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.categoryRepository = categoryRepository;
        this.creditCardRepository = creditCardRepository;
        this.transactionDAO = transactionDAO;
    }


    @Override
    public List<Transaction> findAll() {
        log.info("REST request to find all Transactions");

        return this.transactionRepository.findAll();
    }

    @Override
    public Transaction findOne(Long id) {
        log.info("REST request to find one BankAccount by id");

        if (id == null)
            return null;
        return this.transactionDAO.findById(id);
    }

    @Override
    public TransactionsByBankAccountResponse findAllTransactionsByDateRangeByIdBankAccount(Long idBankAccount, Map<String, String> map1) {
        return null;
    }

    /**
     *
     * @param idUser
     * @param map1
     * @return
     */
    @Override
    public TransactionsUserResponse findAllTransactionsByDateRangeByIdUser(Long idUser, Map<String, String> map1) {
        try {

            if (map1.get("startDate") != null && map1.get("endDate") != null) {
                return this.transactionDAO.findAllTransactionsByDateRangeByIdUser(idUser, map1);
            }

            return new TransactionsUserResponse("-404");

        }catch (Exception e){

            log.error(e.getMessage());
            return new TransactionsUserResponse("-500");
        }
    }

    /**
     * Create a new transaction in database - Service
     * @param transactionDTO to update
     * @return Transaction created in database
     */
    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        log.debug("Create Transaction: {}", transactionDTO);

        Transaction transactionValidated = createValidateTransaction(transactionDTO);

        try{

        transactionDTO.setCreatedDate(Instant.now());
        transactionDTO.setLastModified(Instant.now());

            return transactionRepository.save(transactionValidated);

        }catch(Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            Transaction transactiondError = new Transaction();
            transactiondError.setId(-500L);

            return transactiondError;        }

    }


    /**
     * It update a transaction of database - Service
     * @param transactionDTO to update
     * @return Transaction updated in database
     */
    @Override
    public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {

        log.debug("Update a Transaction: {}", transactionDTO);

        try {

            Transaction transactionValidated = updateValidateTransaction(id, transactionDTO);

            if (transactionValidated.getId() == null){
                Transaction transactionError = new Transaction();
                transactionError.setId(-404L);
                return transactionError;
            }


            return transactionRepository.save(transactionValidated);

        }catch (Exception e){

            log.error(e.getMessage());
            Transaction transactionError = new Transaction();
            transactionError.setId(-500L);

            return transactionError;
        }
    }


    @Override
    public void deleteTransaction(Transaction transactionToDelete){
        log.info("REST request to delete an Transaction by id");
        this.transactionRepository.deleteById(transactionToDelete.getId());

    }

    /**
     * Validate a transaction before to save in db
     * @param transactionDTO
     * @return Transaction
     */
    private Transaction createValidateTransaction (TransactionDTO transactionDTO){

        Transaction transactionEmpty = new Transaction();

        if ( transactionDTO.getConcepto() == null || transactionDTO.getImporte() == null || transactionDTO.getTipoMovimiento() == null || ValidateTypeOfMovimiento(transactionDTO.getTipoMovimiento()) != true){
            return transactionEmpty;
        }
        if ( transactionDTO.getIdBankAccount() == null && transactionDTO.getIdCreditCard() == null){
            return transactionEmpty;
        }

        else {
             // If passed all validations

            Transaction transaction = new Transaction();

            transaction.setConcepto(transactionDTO.getConcepto());
            transaction.setImporte(transactionDTO.getImporte());
            transaction.setTipoMovimiento(transactionDTO.getTipoMovimiento());
            transaction.setCreatedDate(Timestamp.from(Instant.now()));

            if (transactionDTO.getIdBankAccount() != null) {
                Optional<BankAccount> bankAccount = bankAccountRepository.findOneById(transactionDTO.getIdBankAccount());
                transaction.setBankAccount(bankAccount.get());
            }
            if (transactionDTO.getIdCategory() != null) {
                Optional<Category> category = categoryRepository.findOneById(transactionDTO.getIdCategory());
                transaction.setCategory(category.get());
            }

            if (transactionDTO.getIdCreditCard() != null) {
                Optional<CreditCard> creditCard = creditCardRepository.findOneById(transactionDTO.getIdCreditCard());
                transaction.setCreditCard(creditCard.get());
            }

            // Function to set current balance in the BankAccounts and in Transaction before an operation
            currentBalance(transaction);


            return transaction;
        }


    }

    /**
     * Validate a Transaction before to update in db
     * @param transactionDTO
     * @return Transaction
     */
    private Transaction updateValidateTransaction (Long id, TransactionDTO transactionDTO){

        if ( transactionDTO.getConcepto() == null || transactionDTO.getImporte() == null || transactionDTO.getTipoMovimiento() == null || transactionDTO.getCreatedDate() == null){
            return new Transaction();


            }else {

            // Exist ?
            Optional<Transaction> transactionDB = this.transactionRepository.findById(id);

            if (ObjectUtils.isEmpty(transactionDB))
                return new Transaction();


            // Is not possible to modify the importe of the transaction
            if (!transactionDTO.getImporte().equals(transactionDB.get().getImporte()))
                return new Transaction();

            // Is not possible to modify the concepto of the transaction
            if (!transactionDTO.getConcepto().equals(transactionDB.get().getConcepto()))
                return new Transaction();

            // Is not possible to modify the Date of the transaction
            if (!transactionDTO.getCreatedDate().equals(transactionDB.get().getCreatedDate()))
                return new Transaction();

            if (transactionDTO.getIdBankAccount() != null) {
                // Is not possible to modify the bankaccount own of the transaction
                if (!transactionDTO.getIdBankAccount().equals(transactionDB.get().getBankAccount().getId()))
                    return new Transaction();
            }

            if (transactionDTO.getIdCategory() != null) {
                // Is not possible to modify the category own of the transaction
                if (!transactionDTO.getIdCategory().equals(transactionDB.get().getCategory().getId()))
                    return new Transaction();
            }

            if (transactionDTO.getIdCreditCard() != null) {
                // Is not possible to modify the creditcard own of the transaction
                if (!transactionDTO.getCreatedDate().equals(transactionDB.get().getCreditCard().getId()))
                    return new Transaction();
            }

            // Only is possible to modify in a transaction el tipo de movimiento
            transactionDB.get().setTipoMovimiento(transactionDTO.getTipoMovimiento());
            transactionDB.get().setLastModified(Instant.now());

            return transactionDB.get();
        }


    }


    /**
     * Set the current balance in the BankAccounts and Transaction before an operation
     * @param transaction
     * @return Transaction
     */
    private Transaction currentBalance (Transaction transaction) {


        if(transaction.getTipoMovimiento().equals(MovimientoType.PAGO) || transaction.getTipoMovimiento().equals(MovimientoType.RECIBO)){
            transaction.getBankAccount().setBalance(transaction.getBankAccount().getBalance() - transaction.getImporte());
        }
        if (transaction.getTipoMovimiento().equals(MovimientoType.TRANSFERENCIA) || transaction.getTipoMovimiento().equals(MovimientoType.ABONO)) {
            transaction.getBankAccount().setBalance( transaction.getBankAccount().getBalance() + transaction.getImporte());
        }
        transaction.setBalanceAfterTransaction(transaction.getBankAccount().getBalance());

        return transaction;
    }

    /**
     * Check if the type of movimiento is a right field
     * @param movimientoType
     * @return Boolean
     */
    private Boolean ValidateTypeOfMovimiento(Enum movimientoType){

        return (movimientoType).equals(MovimientoType.PAGO) || movimientoType.equals(MovimientoType.RECIBO) || movimientoType.equals(MovimientoType.TRANSFERENCIA) || movimientoType.equals(MovimientoType.ABONO);
        }

}
