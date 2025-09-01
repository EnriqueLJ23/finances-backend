package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dto.DashboardSummary;
import com.jasso.finance.finance.dto.ExpenseCategory;
import com.jasso.finance.finance.dto.CashflowData;

import java.util.List;

public interface DashboardService {
    DashboardSummary getDashboardSummary(Integer userId);
    List<ExpenseCategory> getExpenseCategories(Integer userId);
    List<CashflowData> getCashflowData(Integer userId, int months);
}