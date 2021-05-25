package com.example.application.backend.controller;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.BankAccountDTO;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.service.BankAccountService;

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
import java.util.*;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    private final Logger log = LoggerFactory.getLogger(BankAccount.class);

    private final BankAccountService bankAccountService;

    private final BankAccountRepository bankAccountRepository;

    public BankAccountController(BankAccountService bankAccountService, BankAccountRepository bankAccountRepository) {
        this.bankAccountService = bankAccountService;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Get all bank accounts
     * @return List of bank accounts from database
     */
    @GetMapping("/bankaccounts")
    @ApiOperation("Get all BankAccounts")
    public ResponseEntity<List<BankAccount>> findAll(
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("page", page);
        map1.put("limit", limit);

        List<BankAccount> bankAccountsDB = bankAccountService.findAll(map1);

        if (bankAccountsDB.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (bankAccountsDB.get(0).getId()== -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(bankAccountsDB);
    }

    /**
     * Get bank account by ID
     * @param id Primary key of Bank Account: Long
     * @return Bank account from database
     */
    @GetMapping("/bankaccounts/{id}")
    @ApiOperation("Get bank account by Id")
    public ResponseEntity<BankAccount> findOne(@ApiParam("Primary key of bank account: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<BankAccount> bankAccountOpt = bankAccountService.findOne(id);

        if(!bankAccountOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (bankAccountOpt.get().getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(bankAccountOpt.get());
    }

    /**
     * Create a new bank account in database
     * @param bankAccountDTO BankAccountDTO to create
     * @return bankAccount BankAccount created
     * @throws URISyntaxException
     */
    @PostMapping("/bankaccounts")
    @ApiOperation("Create a new bank account in DB")
    public ResponseEntity<BankAccount> createBankAccount(@ApiParam("bankAccount that you want to create: BankAccountDTO") @RequestBody BankAccountDTO bankAccountDTO) throws URISyntaxException {

        if(ObjectUtils.isEmpty(bankAccountDTO))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        BankAccount result = bankAccountService.createOne(bankAccountDTO);

        if (result.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == 500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/bankaccounts/" + result.getId()))
                .body(result);
    }

    /**
     * It update a bank account of database
     * @param bankAccountDTO BankAccountDTO to update
     * @return CreditCard updated in database
     */
    @PutMapping("/bankaccounts/{id}")
    @ApiOperation("Update only enabled field of BankAccount in DB")
    public ResponseEntity<BankAccount> updateOne(
            @ApiParam("id of BankAccount that you want to update: Long") @PathVariable Long id,
            @ApiParam("BankAccount that you want to update: BankAccountDTO") @RequestBody BankAccountDTO bankAccountDTO
    ){

        if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        BankAccount result = bankAccountService.updateOne(id, bankAccountDTO);

        if (result.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete bank account of database by ID. Really update the "deleted" field
     * @param id bank account primary key that you want to delete
     * @return void
     */
    @DeleteMapping("/bankaccounts/{id}")
    @ApiOperation("Delete bank account of DB by Id")
    public ResponseEntity<Void> deleteOne(@ApiParam("Primary key of bank account: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Boolean> result = bankAccountService.deleteOne(id);

        if (Objects.equals(result, Optional.of(false)))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().build();
    }

}
