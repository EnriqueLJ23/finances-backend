package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dao.BudgetDAO;
import com.jasso.finance.finance.entity.Budget;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService{
    //inject the data access object for budget
    private BudgetDAO budgetDAO;

    @Autowired
    public BudgetServiceImpl(BudgetDAO theBudgetDao) {
        budgetDAO = theBudgetDao;
    }

    @Override
    public List<Budget> findAll(Long id) {
        List<Budget> budgets = budgetDAO.findAll(id);
        for (Budget budget : budgets) {
            BigDecimal usedAmount = budgetDAO.calculateUsedAmount(
                    budget.getUser_id(),
                    budget.getCategory(),
                    budget.getMonth(),
                    budget.getYear()
            );
            budget.setUsed(usedAmount);
        }
        return budgets;
    }

    @Override
    public List<Budget> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year) {
        List<Budget> budgets = budgetDAO.findByUserIdAndMonthAndYear(userId, month, year);
        for (Budget budget : budgets) {
            BigDecimal usedAmount = budgetDAO.calculateUsedAmount(
                    budget.getUser_id(),
                    budget.getCategory(),
                    budget.getMonth(),
                    budget.getYear()
            );
            budget.setUsed(usedAmount);
        }
        return budgets;
    }

    @Override
    public Budget findById(Integer id) {
        return budgetDAO.findById(id);
    }

    @Transactional
    @Override
    public Budget save(Budget theBudget) {
        Budget tempBudget =budgetDAO.save(theBudget);
        return tempBudget;
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        budgetDAO.remove(id);
    }


}
