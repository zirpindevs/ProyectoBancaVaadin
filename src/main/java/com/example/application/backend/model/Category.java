package com.example.application.backend.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Primary key: Long")
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty("Name of Category: String")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @ApiModelProperty("List of transactions that a category has: List<Transaction>")
    private List<Transaction> transactions = new ArrayList<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                '}';
    }
}
