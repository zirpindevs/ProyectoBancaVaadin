package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "bank_loans")
public class BankLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty("Bank Loan amount: Double")
    private Double amount;

    @Column(nullable = false)
    @ApiModelProperty("Bank Loan duration in months: Integer")
    private Integer duration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bank_account_income", nullable = false)
    @JsonIgnore
    @ApiModelProperty("Bank account where the income of the loan is done: BankAccount")
    private BankAccount bankAccountIncome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bank_account_charge", nullable = false)
    @JsonIgnore
    @ApiModelProperty("Bank account where the charge of the loan is done: BankAccount")
    private BankAccount bankAccountCharge;

    @Column(nullable = false)
    @ApiModelProperty("Interest rate of the bank loan: Double")
    private Double interestRate;

    public BankLoan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BankAccount getBankAccountIncome() {
        return bankAccountIncome;
    }

    public void setBankAccountIncome(BankAccount bankAccountIncome) {
        this.bankAccountIncome = bankAccountIncome;
    }

    public BankAccount getBankAccountCharge() {
        return bankAccountCharge;
    }

    public void setBankAccountCharge(BankAccount bankAccountCharge) {
        this.bankAccountCharge = bankAccountCharge;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "BankLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", duration=" + duration +
                ", bankAccountIncome=" + bankAccountIncome +
                ", bankAccountCharge=" + bankAccountCharge +
                ", interestRate=" + interestRate +
                '}';
    }
}
