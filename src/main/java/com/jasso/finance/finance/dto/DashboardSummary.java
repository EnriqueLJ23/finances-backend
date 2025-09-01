package com.jasso.finance.finance.dto;

import java.math.BigDecimal;

public class DashboardSummary {
    private BigDecimal currentBalance;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal budgetUtilization;
    
    public DashboardSummary() {}
    
    public DashboardSummary(BigDecimal currentBalance, BigDecimal monthlyIncome, 
                           BigDecimal monthlyExpenses, BigDecimal budgetUtilization) {
        this.currentBalance = currentBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.budgetUtilization = budgetUtilization;
    }
    
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
    
    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }
    
    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
    
    public BigDecimal getBudgetUtilization() {
        return budgetUtilization;
    }
    
    public void setBudgetUtilization(BigDecimal budgetUtilization) {
        this.budgetUtilization = budgetUtilization;
    }
}