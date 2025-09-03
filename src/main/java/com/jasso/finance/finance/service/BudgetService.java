package com.jasso.finance.finance.service;

import com.jasso.finance.finance.entity.Budget;

import java.util.List;

public interface BudgetService {
    List<Budget> findAll(Long id);
    
    List<Budget> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year);

    Budget findById(Integer id);

    Budget save(Budget theBudget);

    void remove(Integer id);
}
