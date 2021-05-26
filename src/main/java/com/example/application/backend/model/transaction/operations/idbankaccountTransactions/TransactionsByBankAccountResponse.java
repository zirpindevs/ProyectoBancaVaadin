package com.example.application.backend.model.transaction.operations.idbankaccountTransactions;

import com.example.application.backend.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsByBankAccountResponse {

    private String status;

    private String startDate;

    private String endDate;

    private Long idBankAccount;

    private List<Transaction> transactions = new ArrayList<>();

    public TransactionsByBankAccountResponse() {
    }

    public TransactionsByBankAccountResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(Long idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "TransactionsByBankAccountResponse{" +
                "status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", idBankAccount=" + idBankAccount +
                ", transactions=" + transactions +
                '}';
    }
}
