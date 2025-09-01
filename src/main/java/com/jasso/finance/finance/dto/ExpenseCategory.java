package com.jasso.finance.finance.dto;

import java.math.BigDecimal;

public class ExpenseCategory {
    private String category;
    private BigDecimal amount;
    
    public ExpenseCategory() {}
    
    public ExpenseCategory(String category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}