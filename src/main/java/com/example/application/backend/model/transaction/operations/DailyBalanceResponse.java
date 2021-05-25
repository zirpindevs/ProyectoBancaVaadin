package com.example.application.backend.model.transaction.operations;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object that contains the daily balance information for a bank account between two dates.
 */
public class DailyBalanceResponse {

    private String status;

    private String startDate;

    private String endDate;

    private Long idBankAccount;

    private List<DailyBalanceRange> dailyBalanceRanges = new ArrayList<>();

    public DailyBalanceResponse() {
    }

    public DailyBalanceResponse(String status) {
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

    public List<DailyBalanceRange> getDailyBalanceRanges() {
        return dailyBalanceRanges;
    }

    public void setDailyBalanceRanges(List<DailyBalanceRange> dailyBalanceRanges) {
        this.dailyBalanceRanges = dailyBalanceRanges;
    }

    @Override
    public String toString() {
        return "DailyBalanceResponse{" +
                "status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", idBankAccount=" + idBankAccount +
                ", dailyBalanceRanges=" + dailyBalanceRanges +
                '}';
    }
}
