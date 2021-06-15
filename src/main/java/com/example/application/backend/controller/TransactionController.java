package com.example.application.backend.controller;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.transaction.operations.TransactionsBankAccountResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.model.transaction.operations.UserDailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.idbankaccountTransactions.TransactionsByBankAccountResponse;
import com.example.application.backend.repository.TransactionRepository;
import com.example.application.backend.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final Logger log = LoggerFactory.getLogger(Transaction.class);

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }


    /**
     * FIND ALL TRANSACTIONS
     * @return List<Transaction>
     */
    @RequestMapping(method = RequestMethod.GET, value = "/transactions")
    @ApiOperation("Get all Transactions")
    public List<Transaction> findAllTransaction(){
        log.debug("REST request to find all Transaction");

        return this.transactionRepository.findAll();
    }

    /**
     * FIND TRANSACTIONS BY ID
     *
     * @param id
     * @return ResponseEntity<Transaction>
     * @throws URISyntaxException
     */
    @GetMapping("/transactions/{id}")
    @ApiOperation("Get Transaction by Id")
    public ResponseEntity<Transaction> findTransactionById(@ApiParam("Primary key of Transaction: Long") @PathVariable Long id) throws URISyntaxException {
        Transaction findTransaction = this.transactionService.findOne(id);

        if (findTransaction == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(findTransaction);

    }

    /**
     * Controller:
     * Get all Transactions between two dates of the a bank account by IdBankAccount
     *
     * @param idBankAccount    The user ID that you want to get list of transactions
     * @param startDate Start date to obtain the transactions
     * @param endDate   End date to obtain the transactions
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return UserDailyBalanceResponse with List of the Transactions between two dates of all user's accounts
     */
    @GetMapping("/transactions/bankaccount/{idBankAccount}")
    @ApiOperation("Get Balance and Total of Transactions per day between two dates")
    public ResponseEntity<TransactionsBankAccountResponse> findAllTransactionsByDateRangeByIdBankAccount(
            @ApiParam("Primary key of the user: Long") @PathVariable Long idBankAccount,
            @ApiParam("Start date: LocalDate") @QueryParam("startDate") String startDate,
            @ApiParam("End date: LocalDate") @QueryParam("endDate") String endDate,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("startDate", startDate + " 00:00:00.000000");
        map1.put("endDate", endDate + " 23:59:59.999999");
        map1.put("page", page);
        map1.put("limit", limit);

        if (startDate == null || endDate == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        TransactionsBankAccountResponse result = transactionService.findAllTransactionsByDateRangeByIdBankAccount(idBankAccount, map1);

        if (result.getStatus() == "-404")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getStatus() == "-500")
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if (result.getStatus() == "-204")
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Controller:
     * Get all Transactions between two dates of the a bank account by IdBankAccount
     *
     * @param idUser    The user ID that you want to get list of transactions
     * @param startDate Start date to obtain the transactions
     * @param endDate   End date to obtain the transactions
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return List<Transaction>
     */
    @GetMapping("/transactions/user/{idUser}")
    @ApiOperation("Get Balance and Total of Transactions per day between two dates")
    public ResponseEntity<TransactionsUserResponse> findAllTransactionsByDateRangeByIdUser(
            @ApiParam("Primary key of the user: Long") @PathVariable Long idUser,
            @ApiParam("Start date: LocalDate") @QueryParam("startDate") String startDate,
            @ApiParam("End date: LocalDate") @QueryParam("endDate") String endDate,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("startDate", startDate + " 00:00:00.000000");
        map1.put("endDate", endDate + " 23:59:59.999999");
        map1.put("page", page);
        map1.put("limit", limit);

        if (startDate == null || endDate == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        TransactionsUserResponse result = transactionService.findAllTransactionsByDateRangeByIdUser(idUser, map1);

        if (result.getStatus() == "-404")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getStatus() == "-500")
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if (result.getStatus() == "-204")
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Create a transaction in database
     * @param transactionDTO TransactionDTO to create
     * @return transaction Transaction created
     * @throws URISyntaxException
     */
    @PostMapping("/transactions")
    @ApiOperation("Create a new Transaction in DB")
    public ResponseEntity<Transaction> createTransaction(@ApiParam("Transaction that you want to create: Transaction") @RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
        log.debug("REST request to create new a Transaction: {} ", transactionDTO);

        if(ObjectUtils.isEmpty(transactionDTO))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        Transaction result = transactionService.createTransaction(transactionDTO);

        if (result.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/transactions/" + result.getId()))
                .body(result);


    }

    /**
     * It update a transaction of database
     *
     * @param transactionDTO TransactionDTO to update
     * @return Transaction updated in database
     */
    @PutMapping("/transactions/{id}")
    @ApiOperation("Update an existing Transaction in DB")
    public ResponseEntity<Transaction> updateTransaction(
            @ApiParam("id of Transaction that you want to update: Long") @PathVariable Long id,
            @ApiParam("Transaction that you want to update: Transaction")@RequestBody TransactionDTO transactionDTO
    ) {

        log.debug("REST request to update one Transaction: {} ", transactionDTO);

        if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Transaction result = transactionService.updateTransaction(id, transactionDTO);

        if (result.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);

    }


    /**
     * DELETE TRANSACTIONS
     * @param id
     * @return
     */
    @DeleteMapping("/transactions/{id}")
    @ApiOperation("Delete transaction of DB by Id")
    public ResponseEntity<Void> deleteTransaction(@ApiParam("Primary key of transaction: Long") @PathVariable Long id){

        Transaction transactionToDelete = this.transactionService.findOne(id);

        if (transactionToDelete.getId() == null) {
            log.warn("transaction not exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.transactionService.deleteTransaction(transactionToDelete);
        return ResponseEntity.noContent().build();
    }

















































































































    /****************************************************************************************************
     */



    /**
     * Controller:
     * Get all Transactions between two dates of the a creditcard by IdCreditcard
     *
     * @param idCreditcard   The creditcard ID that you want to get list of transactions
     * @param startDate Start date to obtain the transactions
     * @param endDate   End date to obtain the transactions
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return List<Transaction>
     */
    @GetMapping("/transactions/creditcard/{idCreditcard}")
    @ApiOperation("Get Total creditcard Transactions per day between two dates")
    public ResponseEntity<TransactionsCreditcardResponse> findAllTransactionsByDateRangeByIdCreditcard(
            @ApiParam("Primary key of the user: Long") @PathVariable Long idCreditcard,
            @ApiParam("Start date: LocalDate") @QueryParam("startDate") String startDate,
            @ApiParam("End date: LocalDate") @QueryParam("endDate") String endDate,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("startDate", startDate + " 00:00:00.000000");
        map1.put("endDate", endDate + " 23:59:59.999999");
        map1.put("page", page);
        map1.put("limit", limit);

        if (startDate == null || endDate == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        TransactionsCreditcardResponse result = transactionService.findAllTransactionsByDateRangeByIdCreditcard(idCreditcard, map1);

        if (result.getStatus() == "-404")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getStatus() == "-500")
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if (result.getStatus() == "-204")
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(result);
    }

}
