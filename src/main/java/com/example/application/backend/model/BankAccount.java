package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(name = "num_account", nullable = false, unique = true, length = 24)
    @ApiModelProperty("Bank account number: Long")
    private String numAccount;

    @Column(nullable = false, columnDefinition = "double default 0" )
    @ApiModelProperty("Current account balance: Double")
    private Double balance;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @ApiModelProperty("Indicates whether the account can be used actually or not: Boolean")
    private Boolean enabled;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @ApiModelProperty("Mark an account as permanently deleted. The system will not allow updating this field, only change " +
            "the value to true through the Delete service. The record is not deleted, it remains as archived.: Boolean")
    private Boolean deleted;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Created date: LocalDateTime, Not null, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Update date: LocalDateTime, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "bankAccounts")
    @JsonIgnore
    @ApiModelProperty("List of users that a bank account has: List<User>")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    @ApiModelProperty("List of transactions that a bank account has: List<Transaction>")
    private List<Transaction> transactions = new ArrayList<>();


    public BankAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", numAccount='" + numAccount + '\'' +
                ", balance=" + balance +
                ", enabled=" + enabled +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
