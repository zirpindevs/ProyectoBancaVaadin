package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * Model used to create and update BankAccounts(BankAccount)
 */
public class BankAccountDTO {

    @ApiModelProperty("Bank account number: Long")
    private String numAccount;

    @ApiModelProperty("Current account balance: Double")
    private Double balance;

    @ApiModelProperty("Indicates whether the account can be used actually or not: Boolean")
    private Boolean enabled;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Created date: LocalDateTime, Not null, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Update date: LocalDateTime, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime updatedAt;

    public BankAccountDTO() {
    }

    public BankAccountDTO(String numAccount, Double balance, Boolean enabled) {
        this.numAccount = numAccount;
        this.balance = balance;
        this.enabled = enabled;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "numAccount='" + numAccount + '\'' +
                ", balance=" + balance +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
