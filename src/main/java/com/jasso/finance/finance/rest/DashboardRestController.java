package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.service.TransactionService;
import com.jasso.finance.finance.service.BudgetService;
import com.jasso.finance.finance.util.SecurityUtil;
import com.jasso.finance.finance.entity.Transaction;
import com.jasso.finance.finance.entity.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;

    @Autowired
    public DashboardRestController(TransactionService transactionService, BudgetService budgetService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
    }

    @GetMapping("/summary")
    public Map<String, Object> getDashboardSummary() {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        
        List<Transaction> userTransactions = transactionService.findByUserId(authenticatedUserId);
        List<Budget> userBudgets = budgetService.findByUserIdAndMonthAndYear(authenticatedUserId, currentMonth, currentYear);
        
        // Calculate current balance (sum of all transactions)
        BigDecimal currentBalance = userTransactions.stream()
                .map(t -> "INCOME".equalsIgnoreCase(t.getType()) ? t.getAmount() : t.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Filter current month transactions
        List<Transaction> currentMonthTransactions = userTransactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate().toString());
                    return transactionDate.getYear() == currentYear && 
                           transactionDate.getMonthValue() == currentMonth;
                })
                .collect(Collectors.toList());
        
        // Calculate monthly income and expenses
        BigDecimal monthlyIncome = currentMonthTransactions.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal monthlyExpenses = currentMonthTransactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calculate budget utilization
        BigDecimal totalBudget = userBudgets.stream()
                .map(Budget::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal budgetUtilization = BigDecimal.ZERO;
        if (totalBudget.compareTo(BigDecimal.ZERO) > 0) {
            budgetUtilization = monthlyExpenses.divide(totalBudget, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("currentBalance", currentBalance);
        summary.put("monthlyIncome", monthlyIncome);
        summary.put("monthlyExpenses", monthlyExpenses);
        summary.put("budgetUtilization", budgetUtilization);
        
        return summary;
    }

    @GetMapping("/cashflow")
    public List<Map<String, Object>> getCashflowData(@RequestParam(defaultValue = "6") int months) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        List<Transaction> userTransactions = transactionService.findByUserId(authenticatedUserId);
        
        LocalDate now = LocalDate.now();
        List<Map<String, Object>> cashflowData = new ArrayList<>();
        
        for (int i = months - 1; i >= 0; i--) {
            YearMonth targetMonth = YearMonth.from(now.minusMonths(i));
            
            BigDecimal income = userTransactions.stream()
                    .filter(t -> {
                        LocalDate transactionDate = LocalDate.parse(t.getDate().toString());
                        YearMonth transactionMonth = YearMonth.from(transactionDate);
                        return transactionMonth.equals(targetMonth) && "INCOME".equalsIgnoreCase(t.getType());
                    })
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            BigDecimal expenses = userTransactions.stream()
                    .filter(t -> {
                        LocalDate transactionDate = LocalDate.parse(t.getDate().toString());
                        YearMonth transactionMonth = YearMonth.from(transactionDate);
                        return transactionMonth.equals(targetMonth) && "EXPENSE".equalsIgnoreCase(t.getType());
                    })
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", targetMonth.getMonth().name().substring(0, 3));
            monthData.put("year", targetMonth.getYear());
            monthData.put("income", income);
            monthData.put("expenses", expenses);
            
            cashflowData.add(monthData);
        }
        
        return cashflowData;
    }
}