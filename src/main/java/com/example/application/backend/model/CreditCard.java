package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty("Num of credit card: String, Not null, Unique")
    private String numCreditCard;

    @Column(nullable = false)
    @ApiModelProperty("Name of user own credit card: String, Not null")
    private String placeholder;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'debito'")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Type of credit card: CreditCardType Enum, Not null")
    private CreditCardType type;

    @Column(name="card_provider", nullable = false)
    @ApiModelProperty("Company provider of credit card(MasterCard...): String")
    private String cardProvider;

    @Column(nullable = false, length = 3)
    @ApiModelProperty("CVV code of credit card: String, Not null, Length = 3")
    private String cvv;

    @Column(nullable = false,length = 4)
    @ApiModelProperty("PIN code of credit card: String, Not null, Length = 4")
    private String pin;

    @Column(name="expiration_date", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("Expiration date: LocalDate, Not null, pattern = 'yyyy-MM-dd'")
    private LocalDate expirationDate;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @ApiModelProperty("Define if the credit card can be used: Boolean, Not null")
    private Boolean enabled;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @ApiModelProperty("Define if the credit card can be used: Boolean, Not null by default false")
    private Boolean deleted;

    @Column(name = "created_at" , nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Created date: LocalDateTime, Not null, pattern='yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Update date: LocalDateTime, pattern='yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    @ApiModelProperty("User to which a single credit card belongs: User")
    private User user;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    @ApiModelProperty("List of transactions that a credit card has: List<Transaction>")
    private List<Transaction> transactions;

    public CreditCard() {
    }

    public CreditCard(String numCreditCard, String placeholder, CreditCardType type, String cardProvider, String cvv, String pin, LocalDate expirationDate, Boolean enabled, Boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<Transaction> transactions) {
        this.numCreditCard = numCreditCard;
        this.placeholder = placeholder;
        this.type = type;
        this.cardProvider = cardProvider;
        this.cvv = cvv;
        this.pin = pin;
        this.expirationDate = expirationDate;
        this.enabled = enabled;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCreditCard() {
        return numCreditCard;
    }

    public void setNumCreditCard(String numCreditCard) {
        this.numCreditCard = numCreditCard;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getCardProvider() {
        return cardProvider;
    }

    public void setCardProvider(String cardProvider) {
        this.cardProvider = cardProvider;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", numCreditCard='" + numCreditCard + '\'' +
                ", placeholder='" + placeholder + '\'' +
                ", type=" + type +
                ", cardProvider='" + cardProvider + '\'' +
                ", cvv='" + cvv + '\'' +
                ", pin='" + pin + '\'' +
                ", expirationDate=" + expirationDate +
                ", enabled=" + enabled +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
