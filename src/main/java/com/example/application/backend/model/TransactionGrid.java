package com.example.application.backend.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

/**
 * Class to load Grid in the view
 */
public class TransactionGrid {

    @ApiModelProperty("Transaction Id: Long")
    private Long id;

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

    @ApiModelProperty("Number of the bank account: String")
    private String numBankAccount;

    @ApiModelProperty("Primary key of transaction own of credit card: Long")
    private Long idCreditCard;

    @ApiModelProperty("Number of the credit card: String")
    private String numCreditCard;

    @ApiModelProperty("Primary key of transaction own of category: Long")
    private Long idCategory;

    public TransactionGrid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public MovimientoType getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(MovimientoType tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Long getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(Long idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public String getNumBankAccount() {
        return numBankAccount;
    }

    public void setNumBankAccount(String numBankAccount) {
        this.numBankAccount = numBankAccount;
    }

    public Long getIdCreditCard() {
        return idCreditCard;
    }

    public void setIdCreditCard(Long idCreditCard) {
        this.idCreditCard = idCreditCard;
    }

    public String getNumCreditCard() {
        return numCreditCard;
    }

    public void setNumCreditCard(String numCreditCard) {
        this.numCreditCard = numCreditCard;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "TransactionGrid{" +
                "id=" + id +
                ", importe=" + importe +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                ", concepto='" + concepto + '\'' +
                ", tipoMovimiento=" + tipoMovimiento +
                ", createdDate=" + createdDate +
                ", lastModified=" + lastModified +
                ", idBankAccount=" + idBankAccount +
                ", numBankAccount='" + numBankAccount + '\'' +
                ", idCreditCard=" + idCreditCard +
                ", numCreditCard='" + numCreditCard + '\'' +
                ", idCategory=" + idCategory +
                '}';
    }
}
