package com.example.application.backend.model.transaction.operations.totalOperations;

import java.math.BigInteger;

public class DailyOperationsRange {

    private String operationDate;

    private BigInteger category;

    private BigInteger totalOperations;

    public DailyOperationsRange() {
    }


    public String getOperationDate() { return operationDate; }

    public void setOperationDate(String operationDate) { this.operationDate = operationDate; }

    public BigInteger getCategory() { return category; }

    public void setCategory(BigInteger category) { this.category = category; }

    public BigInteger getTotalOperations() { return totalOperations; }

    public void setTotalOperations(BigInteger totalOperations) { this.totalOperations = totalOperations; }
}
