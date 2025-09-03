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
    public List<Goal> findAll(Long id) {
        return goalDAO.findAll(id);
    }

    @Override
    public List<Goal> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year) {
        return goalDAO.findByUserIdAndMonthAndYear(userId, month, year);
    }

    @Override
    public Goal findById(Integer id) {
        return goalDAO.findById(id);
    }

    @Override
    public Goal save(Goal theGoal) {
       Goal tempGoal = goalDAO.save(theGoal);
        return tempGoal;
    }

    @Override
    public void remove(Integer id) {
        goalDAO.remove(id);
    }
}
