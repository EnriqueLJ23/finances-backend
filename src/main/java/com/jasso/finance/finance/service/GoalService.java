package com.jasso.finance.finance.service;

import com.jasso.finance.finance.entity.Goal;

import java.util.List;

public interface GoalService {
    List<Goal> findAll();

    Goal findById(Integer id);

    Goal save(Goal theGoal);

    void remove(Integer id);
}
