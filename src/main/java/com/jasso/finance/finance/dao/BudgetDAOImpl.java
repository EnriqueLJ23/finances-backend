package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Budget;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetDAOImpl implements BudgetDAO{
    //define the field for the entity manager
    private EntityManager entityManager;

    //inject the entity manager using constructor injection
    @Autowired
    public BudgetDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    //implement the findAll method
    @Override
    public List<Budget> findAll() {
        //create the query to retrieve all the budgets
        TypedQuery<Budget> theQuery = entityManager.createQuery("FROM Budget", Budget.class);

        //return the query results
        return theQuery.getResultList();
    }

    //implement the findById method
    @Override
    public Budget findById(Integer id) {
        return entityManager.find(Budget.class,id);
    }

    @Override
    public void save(Budget theBudget) {
        entityManager.persist(theBudget);
    }

    @Override
    public void remove(Integer id) {
        //find budget by id
        Budget theBudget = entityManager.find(Budget.class,id);
        //remove the budget
        entityManager.remove(theBudget);
    }
}
