package com.example.application.backend.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditCardDTO {

    @ApiModelProperty("Num of credit card: String, Not null, Unique")
    private String numCreditCard;

    @ApiModelProperty("Name of user own credit card: String, Not null")
    private String placeholder;

    @ApiModelProperty("Type of credit card: CreditCardType Enum, Not null")
    private CreditCardType type;

    @ApiModelProperty("Company provider of credit card(MasterCard...): String")
    private String cardProvider;

    @ApiModelProperty("CVV code of credit card: String, Not null, Length = 3")
    private String cvv;

    @ApiModelProperty("PIN code of credit card: String, Not null, Length = 4")
    private String pin;

    @ApiModelProperty("Expiration date: LocalDate, Not null, pattern = 'yyyy-MM-dd'")
    private LocalDate expirationDate;

    @ApiModelProperty("Define if the credit card can be used: Boolean, Not null")
    private Boolean enabled;

    @ApiModelProperty("Define if the credit card can be used: Boolean, Not null by default false")
    private Boolean deleted;

    @ApiModelProperty("Created date: LocalDateTime, Not null, pattern='yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime createdAt;

    @ApiModelProperty("Update date: LocalDateTime, pattern='yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime updatedAt;

    @ApiModelProperty("Primary key of user own of credit card: Long")
    private Long idUser;

    public CreditCardDTO() {
    }

    public CreditCardDTO(String numCreditCard, String placeholder, CreditCardType type, String cardProvider, String cvv, String pin, LocalDate expirationDate, Boolean enabled, Boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, Long idUser) {
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
        this.idUser = idUser;
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getDeleted() { return deleted; }

    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
}

