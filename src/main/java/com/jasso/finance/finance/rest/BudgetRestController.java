package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.entity.Budget;
import com.jasso.finance.finance.service.BudgetService;
import com.jasso.finance.finance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class BudgetRestController {
    //create field for the budget service
    private final BudgetService budgetService;

    //inject the budget service
    @Autowired
    public BudgetRestController(BudgetService theBudgetService){
        budgetService = theBudgetService;
    }

    @GetMapping("/budgets")
    public List<Budget> getBudgets(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year){
        
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        if (month != null && year != null) {
            return budgetService.findByUserIdAndMonthAndYear(authenticatedUserId, month, year);
        }
        return budgetService.findAll(authenticatedUserId);
    }

    @PostMapping("/budgets")
    public Budget addBudget(@RequestBody Budget theBudget){
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        theBudget.setUser_id(authenticatedUserId);
        Budget newBudget = budgetService.save(theBudget);
        return newBudget;
    }

    @PutMapping("/budgets/{budgetId}")
    public Budget updateBudget(@PathVariable int budgetId, @RequestBody Budget theBudget){
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Budget existingBudget = budgetService.findById(budgetId);
        if(existingBudget == null){
            throw new RuntimeException("Budget id not found - " + budgetId);
        }
        
        if(!existingBudget.getUser_id().equals(authenticatedUserId)){
            throw new SecurityException("Unauthorized access to budget");
        }
        
        theBudget.setId(budgetId);
        theBudget.setUser_id(authenticatedUserId);
        Budget updatedBudget = budgetService.save(theBudget);
        return updatedBudget;
    }

    @DeleteMapping("/budgets/{budgetId}")
    public Map<String, String> deleteBudget(@PathVariable int budgetId){
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Budget tempBudget = budgetService.findById(budgetId);
        if(tempBudget == null){
            throw new RuntimeException("Budget id not found - " + budgetId);
        }
        
        if(!tempBudget.getUser_id().equals(authenticatedUserId)){
            throw new SecurityException("Unauthorized access to budget");
        }
        
        budgetService.remove(budgetId);
        return Map.of("message", "Budget deleted successfully");
    }

}
