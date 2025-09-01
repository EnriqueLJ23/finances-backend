package com.jasso.finance.finance.dto;

import java.math.BigDecimal;

public class CashflowData {
    private String month;
    private int year;
    private BigDecimal income;
    private BigDecimal expenses;
    
    public CashflowData() {}
    
    public CashflowData(String month, int year, BigDecimal income, BigDecimal expenses) {
        this.month = month;
        this.year = year;
        this.income = income;
        this.expenses = expenses;
    }
    
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public BigDecimal getIncome() {
        return income;
    }
    
    public void setIncome(BigDecimal income) {
        this.income = income;
    }
    
    public BigDecimal getExpenses() {
        return expenses;
    }
    
    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }
}