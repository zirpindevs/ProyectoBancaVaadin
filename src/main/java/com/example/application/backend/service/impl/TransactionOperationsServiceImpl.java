package com.example.application.backend.service.impl;

import com.example.application.backend.dao.TransactionOperationsDao;
import com.example.application.backend.dao.UserDao;
import com.example.application.backend.model.transaction.operations.DailyBalanceRange;
import com.example.application.backend.model.transaction.operations.DailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.UserDailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.totalOperations.DailyOperationsRange;
import com.example.application.backend.model.transaction.operations.totalOperations.DailyOperationsResponse;
import com.example.application.backend.model.transaction.operations.totalTransactions.DailyTransactionRange;
import com.example.application.backend.model.transaction.operations.totalTransactions.DailyTransactionResponse;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.repository.CreditCardRepository;
import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.TransactionOperationsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TransactionOperationsServiceImpl implements TransactionOperationsService {

    private final Logger log = LoggerFactory.getLogger(TransactionOperationsServiceImpl.class);

    private final TransactionOperationsDao transactionOperationsDao;

    private final BankAccountRepository bankAccountRepository;

    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;

    private final UserDao userDao;

    public TransactionOperationsServiceImpl(TransactionOperationsDao transactionOperationsDao, BankAccountRepository bankAccountRepository, CreditCardRepository creditCardRepository, UserRepository userRepository, UserDao userDao) {
        this.transactionOperationsDao = transactionOperationsDao;
        this.bankAccountRepository = bankAccountRepository;
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
        this.userDao = userDao;
    }


    /**
     * Service:
     * Get the Balance and Total of Transactions per day between two dates for a bank account
     * @param idBankAccount Id of bank account that you have get the balance
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyBalanceResponse with List of the Balance and Total of Transactions per day between two dates from database
     */
    @Override
    public DailyBalanceResponse getDailyBalanceByDateRangeByNumAccount(Long idBankAccount, Map<String, String> map1) {

        try {

            boolean bankAccountExist = bankAccountRepository.existsById(idBankAccount);
            if (!bankAccountExist)
                return new DailyBalanceResponse("-404");


            List result = this.transactionOperationsDao.getDailyBalanceByDateRangeByNumAccount(idBankAccount, map1);

            if (result.size() == 0)
                return new DailyBalanceResponse("-204");

            DailyBalanceResponse dailyBalanceResponse = new DailyBalanceResponse();

            dailyBalanceResponse = transformResultToDailyBalances(idBankAccount, result, map1, dailyBalanceResponse);

            result = this.transactionOperationsDao.getTotalTransactionsByDateRangeByNumAccount(idBankAccount, map1);

            dailyBalanceResponse = transformResultToDailyBalances(idBankAccount, result, map1, dailyBalanceResponse);

            return dailyBalanceResponse;

        }catch (Exception e){

            log.error(e.getMessage());
            return new DailyBalanceResponse("-500");
        }
    }

    /**
     * Transform the result of the database into a response of type DailyBalanceResponse
     * @param result List of the balance and Total of Transactions per day between two dates from database
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyBalanceResponse with List of the balance and Total of Transactions per day between two dates from database
     */
    private DailyBalanceResponse transformResultToDailyBalances(Long idBankAccount, List result, Map<String, String> map1, DailyBalanceResponse dailyBalanceResponse) {

        if (dailyBalanceResponse.getStatus() == null){
            result.forEach(
                    item -> {
                        DailyBalanceRange balance = new DailyBalanceRange();

                        Object[] transactionDate = ((Object[]) item);
                        Double balanceDay = (Double) ((Object[]) item)[1];

                        balance.setDate((Timestamp) transactionDate[0]);
                        balance.setBalance(balanceDay);
                        dailyBalanceResponse.getDailyBalanceRanges().add(balance);
                    }
            );

            dailyBalanceResponse.setStatus("ok");
            dailyBalanceResponse.setIdBankAccount(idBankAccount);
            dailyBalanceResponse.setStartDate(map1.get("startDate"));
            dailyBalanceResponse.setEndDate( map1.get("endDate"));
        }else{
            AtomicReference<Integer> i = new AtomicReference<>(0);

            result.forEach(
                    item -> {

                        BigInteger totalTransactions = (BigInteger) ((Object[]) item)[0];
                        dailyBalanceResponse.getDailyBalanceRanges().get(i.get()).setTotalTransactions(totalTransactions);
                        i.getAndSet(i.get() + 1);
                    }
            );
        }

        return dailyBalanceResponse;
    }

    /**
     * Get Balance of all bank accounts that a user has
     * @param idUser Id user
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return UserDailyBalanceResponse with balance of all bank accounts that user has
     */
    @Override
    public UserDailyBalanceResponse getDailyBalanceByDateRangeByUser(Long idUser, Map<String, String> map1) {

        try {

            boolean userExist = userRepository.existsById(idUser);
            if (!userExist)
                return new UserDailyBalanceResponse("-404");


            List bankAccountsByUser = this.userDao.findAllBankAccountsByUser(idUser, map1);

            if (bankAccountsByUser.size() == 0)
                return new UserDailyBalanceResponse("-204");

            UserDailyBalanceResponse userDailyBalanceResponse = createUserDailyBalanceResponse(bankAccountsByUser, idUser, map1);

            return userDailyBalanceResponse;

        }catch (Exception e){

            log.error(e.getMessage());
            return new UserDailyBalanceResponse("-500");
        }
    }

    /**
     * Create a Object of UserDailyBalanceResponse
     * @param bankAccountsByUser Bank accounts that user has
     * @param idUser Id user
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyBalanceResponse with the balance and Total of Transactions per day between two dates of all user's bank accounts
     */
    private UserDailyBalanceResponse createUserDailyBalanceResponse(List bankAccountsByUser, Long idUser, Map<String, String> map1){

        UserDailyBalanceResponse userDailyBalanceResponse = new UserDailyBalanceResponse();

        bankAccountsByUser.forEach(
                item -> {

                    BigInteger idBankAccount = (BigInteger) item;

                    Long idBankAccountLong = idBankAccount.longValue();
                    DailyBalanceResponse bankAccountBalance = this.getDailyBalanceByDateRangeByNumAccount(idBankAccountLong, map1);

                    userDailyBalanceResponse.getBankAccountBalance().add(bankAccountBalance);

                }
        );

        userDailyBalanceResponse.setStatus("200");
        userDailyBalanceResponse.setUserId(idUser);
        userDailyBalanceResponse.setStartDate(map1.get("startDate"));
        userDailyBalanceResponse.setEndDate(map1.get("endDate"));

        return userDailyBalanceResponse;
    }

    /**
     * Service:
     * Get the number of transactions per day between two dates for a creditcard
     * @param idCreditCard Id of creditcard that you have get the transactions
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyBalanceResponse with List of the balance per day of transactions between two dates from database
     */
    @Override
    public DailyTransactionResponse getDailyTransactionByDateRangeByCreditCard(Long idCreditCard, Map<String, String> map1) {

        try {

            boolean creditCardExist = creditCardRepository.existsById(idCreditCard);
            if (!creditCardExist)
                return new DailyTransactionResponse("-404");


            List result = this.transactionOperationsDao.getDailyTransactionByDateRangeByCreditCard(idCreditCard, map1);

            if (result.size() == 0)
                return new DailyTransactionResponse("-204");

            DailyTransactionResponse dailyTransactionResponse = transformResultToDailyTransaction(result, map1);

            return dailyTransactionResponse;

        }catch (Exception e){

            log.error(e.getMessage());
            return new DailyTransactionResponse("-500");
        }
    }

    /**
     * Transform the result of the database into a response of type DailyBalanceResponse
     * @param result List of the balance per day of transactions between two dates from database
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyTransactionResponse with the information of the Total number of transactions per day of a bank
     * account between two dates.
     */
    private DailyTransactionResponse transformResultToDailyTransaction(List result, Map<String, String> map1) {

        DailyTransactionResponse dailyTransactionResponse = new DailyTransactionResponse("ok");

        dailyTransactionResponse.setStartDate(map1.get("startDate"));
        dailyTransactionResponse.setEndDate( map1.get("endDate"));


        result.forEach(
                item -> {
                    DailyTransactionRange transaction = new DailyTransactionRange();

                    Object transactionDate = ((Object[]) item)[1];
                    BigInteger totalTransactions = (BigInteger) ((Object[]) item)[0];

                    transaction.setTransactionDate(transactionDate.toString());
                    transaction.setTotalTransactions(totalTransactions);
                    dailyTransactionResponse.getDailyTransactionRanges().add(transaction);
                }
        );

        return dailyTransactionResponse;
    }

    /**
     * Service:
     * Get the number of operations per day between two dates for a bank account
     * @param map1 Map<String, String> with DateRange params and pagination optionals params
     * @return DailyOperationsResponse with List of the operations per day by category between two dates from database
     */
    @Override
        public DailyOperationsResponse getAllOperationsByCategoryBankAccount(Long idBankAccount, Map<String, String> map1) {

            try {

                boolean bankAccountExist = bankAccountRepository.existsById(idBankAccount);
                if (!bankAccountExist)
                    return new DailyOperationsResponse("-404");


                List result = this.transactionOperationsDao.getAllOperationsByCategoryBankAccount(idBankAccount, map1);

                if (result.size() == 0)
                    return new DailyOperationsResponse("-204");

                DailyOperationsResponse dailyOperationsResponse = transformResultToDailyOperation(result, map1);

                return dailyOperationsResponse;

            }catch (Exception e){

                log.error(e.getMessage());
                return new DailyOperationsResponse("-500");
            }
        }

        /**
         * Service:
         * Get the number of operations per day between two dates for a credit card
         * @param map1 Map<String, String> with DateRange params and pagination optionals params
         * @return DailyOperationsResponse with List of the operations per day by category between two dates from database
         */
        @Override
        public DailyOperationsResponse getAllOperationsByCategoryCreditCard(Long idCreditCard, Map<String, String> map1){
            try {

                boolean creditCardExist = creditCardRepository.existsById(idCreditCard);
                if (!creditCardExist)
                    return new DailyOperationsResponse("-404");


                List result = this.transactionOperationsDao.getAllOperationsByCategoryCreditCard(idCreditCard, map1);

                if (result.size() == 0)
                    return new DailyOperationsResponse("-204");

                DailyOperationsResponse dailyOperationsResponse = transformResultToDailyOperation(result, map1);

                return dailyOperationsResponse;

            }catch (Exception e){

                log.error(e.getMessage());
                return new DailyOperationsResponse("-500");
            }
        }

        /**
         * Transform the result of the database into a response of type DailyOperationsResponse
         * @param result List of operations per day in a bankaccount between two dates from database
         * @param result List of operations per day in a between two dates from database
         * @param map1 Map<String, String> with DateRange params and pagination optionals params
         * @return DailyOperationsResponse with List of the operations per day in a bankaccount between two dates from database
         * @return DailyOperationsResponse with List of the operations per day between two dates from database
         */
        private DailyOperationsResponse transformResultToDailyOperation(List result, Map<String, String> map1) {

            DailyOperationsResponse dailyOperationsResponse = new DailyOperationsResponse("ok");

            dailyOperationsResponse.setStartDate(map1.get("startDate"));
            dailyOperationsResponse.setEndDate( map1.get("endDate"));


            result.forEach(
                    item -> {
                        DailyOperationsRange operation = new DailyOperationsRange();

                        BigInteger category = (BigInteger) ((Object[]) item)[0];
                        BigInteger totalTransactions = (BigInteger) ((Object[]) item)[1];
                        Object transactionDate = ((Object[]) item)[2];

                        operation.setCategory(category);
                        operation.setTotalOperations(totalTransactions);
                        operation.setOperationDate(transactionDate.toString());
                        dailyOperationsResponse.getDailyOperationsRanges().add(operation);
                    }
            );

            return dailyOperationsResponse;
        }

}
