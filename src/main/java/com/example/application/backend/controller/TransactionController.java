package com.example.application.backend.controller;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
}
