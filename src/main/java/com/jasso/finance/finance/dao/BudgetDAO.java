package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Budget;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetDAO {

    List<Budget> findAll(Integer id);
    
    List<Budget> findByUserIdAndMonthAndYear(Integer userId, Integer month, Integer year);

    Budget findById(Integer id);

    Budget save(Budget theBudget);

    void remove(Integer id);

    BigDecimal calculateUsedAmount(String userId, String category, int month, int year);
}
