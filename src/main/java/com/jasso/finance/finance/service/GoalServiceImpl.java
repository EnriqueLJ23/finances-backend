package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dao.GoalDAO;
import com.jasso.finance.finance.entity.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    //create field for the Goal DAO
    private GoalDAO goalDAO;

    //inject the GoalDAO with the constructor
    @Autowired
    public GoalServiceImpl(GoalDAO theGoalDAO) {
        goalDAO =theGoalDAO;
    }

    @Override
    public List<Goal> findAll() {
        return goalDAO.findAll();
    }

    @Override
    public Goal findById(Integer id) {
        return goalDAO.findById(id);
    }

    @Override
    public void save(Goal theGoal) {
        goalDAO.save(theGoal);
    }

    @Override
    public void remove(Integer id) {
        goalDAO.remove(id);
    }
}
