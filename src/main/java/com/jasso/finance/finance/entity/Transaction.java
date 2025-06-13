package com.jasso.finance.finance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
//fields of the table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "type")
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    //constructors
    public Transaction(){}

    public Transaction(String user_id, BigDecimal amount, String type, String category, String description, LocalDate date) {
        this.user_id = user_id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    //getter and setters


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
