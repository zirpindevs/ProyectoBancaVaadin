package com.example.application.backend.model.bankaccount.operations;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.TransactionGrid;

import java.util.ArrayList;
import java.util.List;

public class BankAccountUserResponse {
    private String status;

    private List<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccountUserResponse() {
    }

    public BankAccountUserResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public String toString() {
        return "BankAccountUserResponse{" +
                "status='" + status + '\'' +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}