package com.example.application.backend.model.transaction.operations;

import java.util.ArrayList;
import java.util.List;

/**
 * Response class that contains the information of the daily balance of all the bank accounts of a user between two dates.
 */
public class UserDailyBalanceResponse {

    private String status;

    private Long userId;

    private String startDate;

    private String endDate;

    private List<DailyBalanceResponse> bankAccountBalance = new ArrayList<>();

    public UserDailyBalanceResponse() {
    }

    public UserDailyBalanceResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<DailyBalanceResponse> getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(List<DailyBalanceResponse> bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    @Override
    public String toString() {
        return "UserDailyBalanceResponse{" +
                "status='" + status + '\'' +
                ", userId=" + userId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", bankAccountBalance=" + bankAccountBalance +
                '}';
    }
}
