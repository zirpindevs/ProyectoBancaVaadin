package com.example.application.backend.model.transaction.operations.totalTransactions;

import java.math.BigInteger;

/**
 * Class containing the total number of transactions on a given day.
 */
public class DailyTransactionRange {

    private String transactionDate;

    private BigInteger totalTransactions;

    public DailyTransactionRange() {
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigInteger getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(BigInteger totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
