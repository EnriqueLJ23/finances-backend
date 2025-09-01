package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.dto.DashboardSummary;
import com.jasso.finance.finance.dto.ExpenseCategory;
import com.jasso.finance.finance.dto.CashflowData;
import com.jasso.finance.finance.service.DashboardService;
import com.jasso.finance.finance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {
    
    private DashboardService dashboardService;
    
    @Autowired
    public DashboardRestController(DashboardService theDashboardService) {
        dashboardService = theDashboardService;
    }
    
    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary() {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        return dashboardService.getDashboardSummary(authenticatedUserId.intValue());
    }
    
    @GetMapping("/expense-categories")
    public List<ExpenseCategory> getExpenseCategories() {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        return dashboardService.getExpenseCategories(authenticatedUserId.intValue());
    }
    
    @GetMapping("/cashflow")
    public List<CashflowData> getCashflowData(@RequestParam(defaultValue = "6") int months) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        return dashboardService.getCashflowData(authenticatedUserId.intValue(), months);
    }
}