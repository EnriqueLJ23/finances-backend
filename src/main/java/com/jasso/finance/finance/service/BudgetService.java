package com.jasso.finance.finance.service;

import com.jasso.finance.finance.entity.Budget;

import java.util.List;

public interface BudgetService {
    List<Budget> findAll();

    Budget findById(Integer id);

    void save(Budget theBudget);

    void remove(Integer id);
}
