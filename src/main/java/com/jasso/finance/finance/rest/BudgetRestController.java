package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.entity.Budget;
import com.jasso.finance.finance.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetRestController {
    //create field for the budget service
    private BudgetService budgetService;

    //inject the budget service
    @Autowired
    public BudgetRestController(BudgetService theBudgetService){
        budgetService = theBudgetService;
    }

    @GetMapping("/budgets")
    public List<Budget> getBudgets(){
        return budgetService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/testing")
    public String tests(){
        return "Testing the api auth";
    }

}
