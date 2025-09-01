package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Goal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoalDAOImpl implements GoalDAO{
    //create field for entity manager
    private EntityManager entityManager;

    //inject the entity manager with the cosntructor injection
    @Autowired
    public GoalDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Goal> findAll(Integer id) {
        //retrieve the goals
        TypedQuery<Goal> query = entityManager.createQuery("FROM Goal g WHERE g.user_id = :userId", Goal.class);
        query.setParameter("userId", id);
        //return them in a list
        return query.getResultList();
    }

    @Override
    public List<Goal> findByUserIdAndMonthAndYear(Integer userId, Integer month, Integer year) {
        TypedQuery<Goal> query = entityManager.createQuery(
            "FROM Goal g WHERE g.user_id = :userId AND MONTH(g.date) = :month AND YEAR(g.date) = :year", 
            Goal.class
        );
        query.setParameter("userId", userId);
        query.setParameter("month", month);
        query.setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public Goal findById(Integer id) {
        return entityManager.find(Goal.class,id);
    }

    @Transactional
    @Override
    public Goal save(Goal theGoal) {

        Goal tempGoal = entityManager.merge(theGoal);
        return tempGoal;
    }
@Transactional
    @Override
    public void remove(Integer id) {
        //find goal by id
        Goal theGoal = entityManager.find(Goal.class,id);

        //remove the goal from the database
        entityManager.remove(theGoal);
    }
}
