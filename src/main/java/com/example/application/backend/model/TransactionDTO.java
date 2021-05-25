package com.example.application.backend.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

public class TransactionDTO {


    @ApiModelProperty("Transaction amount: Double")
    private Double importe;

    @ApiModelProperty("Balance after each transaction: Double")
    private Double balanceAfterTransaction;

    @ApiModelProperty("Transaction description: String")
    private String concepto;

    @ApiModelProperty("Type of transaction: String")
    private MovimientoType tipoMovimiento;

    @ApiModelProperty("Created date: Instant")
    private Instant createdDate;

    @ApiModelProperty("Transaction update date: Instant")
    private Instant lastModified;

    @ApiModelProperty("Primary key of transaction own of bankaccount: Long")
    private Long idBankAccount;

    @ApiModelProperty("Primary key of transaction own of credit card: Long")
    private Long idCreditCard;

    @ApiModelProperty("Primary key of transaction own of category: Long")
    private Long idCategory;

    public TransactionDTO() {
    }

    public TransactionDTO(Double importe, Double balanceAfterTransaction, String concepto, MovimientoType tipoMovimiento, Instant createdDate, Instant lastModified, Long idBankAccount, Long idCreditCard, Long idCategory) {
        this.importe = importe;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.concepto = concepto;
        this.tipoMovimiento = tipoMovimiento;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
        this.idBankAccount = idBankAccount;
        this.idCreditCard = idCreditCard;
        this.idCategory = idCategory;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getBalanceAfterTransaction() { return balanceAfterTransaction; }

    public void setBalanceAfterTransaction(Double balanceAfterTransaction) { this.balanceAfterTransaction = balanceAfterTransaction; }

    public String getConcepto() { return concepto; }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public MovimientoType getTipoMovimiento() { return tipoMovimiento; }

    public void setTipoMovimiento(MovimientoType tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModified() { return lastModified; }

    public void setLastModified(Instant lastModified) { this.lastModified = lastModified; }

    public Long getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(Long idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public Long getIdCreditCard() {
        return idCreditCard;
    }

    public void setIdCreditCard(Long idCreditCard) {
        this.idCreditCard = idCreditCard;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}
