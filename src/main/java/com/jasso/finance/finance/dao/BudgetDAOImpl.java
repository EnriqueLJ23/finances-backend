package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Budget;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    public List<Budget> findAll(Integer id) {
        //create the query to retrieve all the budgets
        TypedQuery<Budget> theQuery = entityManager.createQuery("FROM Budget b WHERE b.user_id = :userId ", Budget.class);
        theQuery.setParameter("userId", id);
        //return the query results
        return theQuery.getResultList();
    }

    @Override
    public List<Budget> findByUserIdAndMonthAndYear(Integer userId, Integer month, Integer year) {
        TypedQuery<Budget> theQuery = entityManager.createQuery(
            "FROM Budget b WHERE b.user_id = :userId AND b.month = :month AND b.year = :year", 
            Budget.class
        );
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("month", month);
        theQuery.setParameter("year", year);
        return theQuery.getResultList();
    }

    //implement the findById method
    @Override
    public Budget findById(Integer id) {
        return entityManager.find(Budget.class,id);
    }

    @Override
    public Budget save(Budget theBudget) {
        Budget tempBudget = entityManager.merge(theBudget);
        return tempBudget;
    }

    @Override
    public void remove(Integer id) {
        //find budget by id
        Budget theBudget = entityManager.find(Budget.class,id);
        //remove the budget
        entityManager.remove(theBudget);
    }

    @Override
    public BigDecimal calculateUsedAmount(String userId, String category, int month, int year) {
        String jpql = """
            SELECT COALESCE(SUM(ABS(t.amount)), 0) 
            FROM Transaction t 
            WHERE t.user_id = :userId 
                AND t.category = :category 
                AND MONTH(t.date) = :month 
                AND YEAR(t.date) = :year 
                AND t.type = 'EXPENSE'
            """;

        Query query = entityManager.createQuery(jpql);
        query.setParameter("userId", userId);
        query.setParameter("category", category);
        query.setParameter("month", month);
        query.setParameter("year", year);

        Object result = query.getSingleResult();
        return result != null ? (BigDecimal) result : BigDecimal.ZERO;
    }
}
