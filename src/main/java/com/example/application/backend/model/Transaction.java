package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty("Transaction amount: Double")
    private Double importe;

    @Column(name = "balance_after_transaction")
    @ApiModelProperty("Balance after each transaction: Double")
    private Double balanceAfterTransaction;

    @Column(nullable = false)
    @ApiModelProperty("Transaction description: String")
    private String concepto;

    @Column(name="tipo_movimiento")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Type of transaction: String")
    private MovimientoType tipoMovimiento;

    @Column(name="created_date", nullable = false)
    @ApiModelProperty("Created date: Instant")
    private Timestamp createdDate;

    @Column(name="last_modified")
    @ApiModelProperty("Transaction update date: Instant")
    private Instant lastModified;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bank_account", nullable = false)
    @JsonIgnore
    @ApiModelProperty("Bank account to which a single transaction belongs: BankAccount")
    private BankAccount bankAccount;

    @ManyToOne()
    @JoinColumn(name = "id_credit_card")
    @JsonIgnore
    @ApiModelProperty("Credit card to which a single transaction belongs: CreditCard")
    private CreditCard creditCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    @JsonIgnore
    @ApiModelProperty("Category to which a single transaction belongs: Category")
    private Category category;

    public Transaction() {
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

    public Double getBalanceAfterTransaction() { return balanceAfterTransaction; }

    public void setBalanceAfterTransaction(Double balanceAfterTransaction) { this.balanceAfterTransaction = balanceAfterTransaction; }

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

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModified() { return lastModified; }

    public void setLastModified(Instant lastModified) { this.lastModified = lastModified; }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", importe=" + importe +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                ", concepto='" + concepto + '\'' +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", createdDate=" + createdDate +
                ", lastModified=" + lastModified +
                '}';
    }
}
