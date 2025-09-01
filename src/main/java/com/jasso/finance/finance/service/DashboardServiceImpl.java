package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dto.DashboardSummary;
import com.jasso.finance.finance.dto.ExpenseCategory;
import com.jasso.finance.finance.dto.CashflowData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {
    
    private EntityManager entityManager;
    
    @Autowired
    public DashboardServiceImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }
    
    @Override
    @Transactional
    public DashboardSummary getDashboardSummary(Integer userId) {
        // Get current balance (total income - total expenses)
        BigDecimal currentBalance = getCurrentBalance(userId);
        
        // Get current month and year
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        
        // Get monthly income for current month
        BigDecimal monthlyIncome = getMonthlyIncome(userId, currentMonth, currentYear);
        
        // Get monthly expenses for current month
        BigDecimal monthlyExpenses = getMonthlyExpenses(userId, currentMonth, currentYear);
        
        // Calculate budget utilization
        BigDecimal budgetUtilization = getBudgetUtilization(userId, currentMonth, currentYear);
        
        return new DashboardSummary(currentBalance, monthlyIncome, monthlyExpenses, budgetUtilization);
    }
    
    @Override
    @Transactional
    public List<ExpenseCategory> getExpenseCategories(Integer userId) {
        String sql = "SELECT t.category, SUM(t.amount) as total " +
                    "FROM transactions t " +
                    "WHERE t.user_id = :userId AND t.type = 'Expense' " +
                    "GROUP BY t.category " +
                    "ORDER BY total DESC";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", String.valueOf(userId));
        
        List<Object[]> results = query.getResultList();
        List<ExpenseCategory> expenseCategories = new ArrayList<>();
        
        for (Object[] result : results) {
            String category = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            expenseCategories.add(new ExpenseCategory(category, amount));
        }
        
        return expenseCategories;
    }
    
    @Override
    @Transactional
    public List<CashflowData> getCashflowData(Integer userId, int months) {
        List<CashflowData> cashflowData = new ArrayList<>();
        LocalDate now = LocalDate.now();
        
        for (int i = months - 1; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);
            int month = date.getMonthValue();
            int year = date.getYear();
            String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            
            BigDecimal income = getMonthlyIncome(userId, month, year);
            BigDecimal expenses = getMonthlyExpenses(userId, month, year);
            
            cashflowData.add(new CashflowData(monthName, year, income, expenses));
        }
        
        return cashflowData;
    }
    
    private BigDecimal getCurrentBalance(Integer userId) {
        String sql = "SELECT " +
                    "COALESCE(SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END), 0) - " +
                    "COALESCE(SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END), 0) as balance " +
                    "FROM transactions WHERE user_id = :userId";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", String.valueOf(userId));
        
        Object result = query.getSingleResult();
        return result != null ? (BigDecimal) result : BigDecimal.ZERO;
    }
    
    private BigDecimal getMonthlyIncome(Integer userId, int month, int year) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM transactions " +
                    "WHERE user_id = :userId AND type = 'Income' " +
                    "AND EXTRACT(MONTH FROM date) = :month AND EXTRACT(YEAR FROM date) = :year";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", String.valueOf(userId));
        query.setParameter("month", month);
        query.setParameter("year", year);
        
        Object result = query.getSingleResult();
        return result != null ? (BigDecimal) result : BigDecimal.ZERO;
    }
    
    private BigDecimal getMonthlyExpenses(Integer userId, int month, int year) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM transactions " +
                    "WHERE user_id = :userId AND type = 'Expense' " +
                    "AND EXTRACT(MONTH FROM date) = :month AND EXTRACT(YEAR FROM date) = :year";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", String.valueOf(userId));
        query.setParameter("month", month);
        query.setParameter("year", year);
        
        Object result = query.getSingleResult();
        return result != null ? (BigDecimal) result : BigDecimal.ZERO;
    }
    
    private BigDecimal getBudgetUtilization(Integer userId, int month, int year) {
        // Get total budgets for current month/year
        String budgetSql = "SELECT COALESCE(SUM(amount), 0) FROM budgets " +
                          "WHERE user_id = :userId AND month = :month AND year = :year";
        
        Query budgetQuery = entityManager.createNativeQuery(budgetSql);
        budgetQuery.setParameter("userId", String.valueOf(userId));
        budgetQuery.setParameter("month", month);
        budgetQuery.setParameter("year", year);
        
        BigDecimal totalBudget = (BigDecimal) budgetQuery.getSingleResult();
        
        if (totalBudget == null || totalBudget.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        // Get total expenses for current month/year
        BigDecimal totalExpenses = getMonthlyExpenses(userId, month, year);
        
        // Calculate utilization percentage
        return totalExpenses.divide(totalBudget, 4, BigDecimal.ROUND_HALF_UP)
                           .multiply(new BigDecimal("100"));
    }
}