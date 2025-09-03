package com.jasso.finance.finance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "month")
    private int month;

    @Column(name = "year")
    private int year;
    @Transient
    private BigDecimal used;

    //define constructors
    //empty one required by jpa
    public Budget(){}

    //and one for the other data

    public Budget(Long user_id, String category, BigDecimal  amount, int month, int year) {
        this.user_id = user_id;
        this.category = category;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.used = BigDecimal.ZERO;
    }

    //define the getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal  getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal  amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }
}
