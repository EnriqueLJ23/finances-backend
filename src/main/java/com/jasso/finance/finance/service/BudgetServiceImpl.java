package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dao.BudgetDAO;
import com.jasso.finance.finance.entity.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Budget> findAll() {
        return budgetDAO.findAll();
    }

    @Override
    public Budget findById(Integer id) {
        return budgetDAO.findById(id);
    }

    @Override
    public void save(Budget theBudget) {
        budgetDAO.save(theBudget);
    }

    @Override
    public void remove(Integer id) {
        budgetDAO.remove(id);
    }


}
