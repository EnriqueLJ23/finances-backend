package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Goal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public List<Goal> findAll() {
        //retrieve the goals
        TypedQuery<Goal> query = entityManager.createQuery("FROM Goal", Goal.class);
        //return them in a list
        return query.getResultList();
    }

    @Override
    public Goal findById(Integer id) {
        return entityManager.find(Goal.class,id);
    }

    @Override
    public void save(Goal theGoal) {
        entityManager.persist(theGoal);
    }

    @Override
    public void remove(Integer id) {
        //find goal by id
        Goal theGoal = entityManager.find(Goal.class,id);

        //remove the goal from the database
        entityManager.remove(theGoal);
    }
}
