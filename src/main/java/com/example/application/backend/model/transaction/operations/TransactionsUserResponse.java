package com.example.application.backend.model.transaction.operations;

import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

public class TransactionsUserResponse {

    private String status;

    private String startDate;

    private String endDate;

    private Long idUser;

    private List<TransactionDTO> transactions = new ArrayList<>();

    public TransactionsUserResponse() {
    }

    public TransactionsUserResponse(String status) {
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "TransactionsUserResponse{" +
                "status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
