package com.example.application.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty("Nif of user: String, Not null, Unique")
    private String nif;

    @Column(nullable = false,  unique = true)
    @ApiModelProperty("Contact email of User: String, Not null, Unique")
    private String email;

    @Column(nullable = false)
    @ApiModelProperty("Password to Login: String, Not null")
    private String password;

    @Column(nullable = false)
    @ApiModelProperty("Name of user: String, Not null")
    private String name;

    @Column(name="last_name", nullable = false)
    @ApiModelProperty("Last name of user: String, Not null")
    private String lastName;

    @Column(name="number_phone", unique = true)
    @ApiModelProperty("Contact phone of user: String, Unique")
    private String numberPhone;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @ApiModelProperty("Specifies if a user is enabled on the system: Boolean, Not null, Default = true")
    private Boolean enabled;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Created date: LocalDateTime, Not null, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Update date: LocalDateTime, pattern = 'yyyy-MM-dd HH:mm:ss'")
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'pendiente'")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Status of user: UserStatus Enum, Not null, Default = 'pendiente'")
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(name="credit_cards")
    @ApiModelProperty("List of credit cards that a user has: List<CreditCard>")
    private List<CreditCard> creditCards = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_bank_accounts",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="bank_account_id", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "bank_account_id" })}
    )
    @Column(name="bank_accounts")
    @ApiModelProperty("List of bank accounts that a user has: List<BankAccount>")
    private List<BankAccount> bankAccounts = new ArrayList<>();


    public User() {
    }

    public User(String nif, String password) {
        this.nif = nif;
        this.password = password;
    }

    public User(String nif, String email, String password) {
        this.nif = nif;
        this.email = email;
        this.password = password;
    }

    public User(String nif, String email, String password, String name, String lastName, String numberPhone, Boolean enabled) {
        this.nif = nif;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.numberPhone = numberPhone;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", lastModified=" + updatedAt +
                ", status=" + status +
                '}';
    }
}
