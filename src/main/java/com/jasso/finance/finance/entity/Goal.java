package com.jasso.finance.finance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal {
    //define the fields of the table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "target_amount")
    private BigDecimal target_amount;

    @Column(name = "current_amount")
    private BigDecimal current_amount;

    @Column(name = "due_date")
    private LocalDate date;


    //define the contructors
    public Goal(){}

    public Goal(Long user_id, String title, BigDecimal target_amount, BigDecimal current_amount, LocalDate date) {
        this.user_id = user_id;
        this.title = title;
        this.target_amount = target_amount;
        this.current_amount = current_amount;
        this.date = date;
    }

    //define the getter and setters for the entity

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

    public BigDecimal getTarget_amount() {
        return target_amount;
    }

    public void setTarget_amount(BigDecimal target_amount) {
        this.target_amount = target_amount;
    }

    public BigDecimal getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(BigDecimal current_amount) {
        this.current_amount = current_amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
