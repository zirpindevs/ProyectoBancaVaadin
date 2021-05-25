package com.example.application.backend.controller;


import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.transaction.operations.DailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.UserDailyBalanceResponse;
import com.example.application.backend.model.transaction.operations.totalOperations.DailyOperationsResponse;
import com.example.application.backend.model.transaction.operations.totalTransactions.DailyTransactionResponse;
import com.example.application.backend.service.TransactionOperationsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionOperationsController {

    private final Logger log = LoggerFactory.getLogger(Transaction.class);

    private final TransactionOperationsService transactionOperationsService;

    public TransactionOperationsController(TransactionOperationsService transactionOperationsService) {
        this.transactionOperationsService = transactionOperationsService;
    }

    /**
     * Controller:
     * Get the Balance and Total of Transactions per day and the total transactions between two dates for a bank account
     *
     * @param id        Id of bank account that you have get the balance
     * @param startDate Start date to obtain the daily balance
     * @param endDate   End date to obtain the daily balance
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return DailyBalanceResponse with List of the Balance and Total of Transactions per day between two dates from database
     */
    @GetMapping("/transactions-operations/daily-balance-bankaccount/{id}")
    @ApiOperation("Get Balance and Total of Transactions per day between two dates")
    public ResponseEntity<DailyBalanceResponse> getDailyBalanceByDateRangeByNumAccount(
            @ApiParam("Primary key of Bank account: Long") @PathVariable Long id,
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

        DailyBalanceResponse result = transactionOperationsService.getDailyBalanceByDateRangeByNumAccount(id, map1);

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
     * Get the Balance and Total of Transactions per day and the total transactions between two dates for a bank account
     *
     * @param userId    The user ID that you want to get the daily balance of all your accounts
     * @param startDate Start date to obtain the daily balance
     * @param endDate   End date to obtain the daily balance
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return UserDailyBalanceResponse with List of the Balance and Total of Transactions per day between two dates of all user accounts
     */
    @GetMapping("/transactions-operations/daily-balance-user/{userId}")
    @ApiOperation("Get Balance and Total of Transactions per day between two dates")
    public ResponseEntity<UserDailyBalanceResponse> getDailyBalanceByDateRangeByUser(
            @ApiParam("Primary key of the user: Long") @PathVariable Long userId,
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

        UserDailyBalanceResponse result = transactionOperationsService.getDailyBalanceByDateRangeByUser(userId, map1);

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
     * Get the number of transactions per day between two dates for a creditcard
     *
     * @param id        Id of creditcard that you have get the balance
     * @param startDate Start date to obtain the daily balance
     * @param endDate   End date to obtain the daily balance
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return DailyBalanceResponse with contains the information of the Total number of transactions per day of a bank account between two dates.
     */
    @GetMapping("/transactions-operations/total-transactions-creditcard/{id}")
    @ApiOperation("Get number of transactions per day between two dates")
    public ResponseEntity<DailyTransactionResponse> getTotalTransactionByDateRangeByCreditCard(
            @ApiParam("Primary key of credit card: Long") @PathVariable Long id,
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

        DailyTransactionResponse result = transactionOperationsService.getDailyTransactionByDateRangeByCreditCard(id, map1);

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
     * Get the number total of operations per day by category between two dates for a bankaccount
     *
     * @param id        Id of bankaccout that you have get the operations
     * @param startDate Start date to obtain the daily operations
     * @param endDate   End date to obtain the daily operations
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return DailyOperationsResponse with List of the operations per day of a bankaccount between two dates from database
     */
    @GetMapping("/transactions-operations/total-transactions-category-bankaccount/{id}")
    @ApiOperation("Get number of operations per day between two dates")
    public ResponseEntity<DailyOperationsResponse> getTotalOperationsByDateRangeByCategoryByBankAccount(
            @ApiParam("Primary key of bank account: Long") @PathVariable Long id,
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

        DailyOperationsResponse result = transactionOperationsService.getAllOperationsByCategoryBankAccount(id, map1);

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
     * Get the number of operations per day between two dates for a creditcard
     *
     * @param id        Id of creditcard that you have get the operations
     * @param startDate Start date to obtain the daily operations
     * @param endDate   End date to obtain the daily operations
     * @param page      Page to be displayed of the results obtained (optional)
     * @param limit     Number of records per page that you want to show of the results obtained (optional)
     * @return DailyOperationsResponse with List of the operations per day of a creditcard between two dates from database
     */
    @GetMapping("/transactions-operations/total-transactions-category-creditcard/{id}")
    @ApiOperation("Get number of operations per day between two dates")
    public ResponseEntity<DailyOperationsResponse> getTotalTransactiosByDateRangeByCategoryCreditCard(
            @ApiParam("Primary key of credit card: Long") @PathVariable Long id,
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

        DailyOperationsResponse result = transactionOperationsService.getAllOperationsByCategoryCreditCard(id, map1);

        if (result.getStatus() == "-404")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getStatus() == "-500")
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if (result.getStatus() == "-204")
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(result);
    }

}
