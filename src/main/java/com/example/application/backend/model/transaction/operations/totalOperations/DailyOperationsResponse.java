package com.example.application.backend.model.transaction.operations.totalOperations;

import java.util.ArrayList;
import java.util.List;

public class DailyOperationsResponse {

    private String status;

    private String startDate;

    private String endDate;

    private List<DailyOperationsRange> dailyOperationsRanges = new ArrayList<>();

    public DailyOperationsResponse() {
    }

    public DailyOperationsResponse(String status) {
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

    public List<DailyOperationsRange> getDailyOperationsRanges() { return dailyOperationsRanges; }

    public void setDailyOperationsRanges(List<DailyOperationsRange> dailyOperationsRanges) { this.dailyOperationsRanges = dailyOperationsRanges; }

    @Override
    public String toString() {
        return "DailyTransactionResponse{" +
                "status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", dailyOperationsRanges=" + dailyOperationsRanges +
                '}';
    }
}
