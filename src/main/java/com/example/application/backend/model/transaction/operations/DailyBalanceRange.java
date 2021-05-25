package com.example.application.backend.model.transaction.operations;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Class containing the information of a day's balance for a bank account.
 */
public class DailyBalanceRange {

    private Timestamp date;

    private Double balance;

    private BigInteger totalTransactions;

    public DailyBalanceRange() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public BigInteger getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(BigInteger totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    @Override
    public String toString() {
        return "DailyBalanceRange{" +
                "date=" + date +
                ", balance=" + balance +
                ", totalTransactions=" + totalTransactions +
                '}';
    }
}
