package com.jasso.finance.finance.dao;
import com.jasso.finance.finance.entity.Goal;

import java.util.List;

public interface GoalDAO {
    List<Goal> findAll(Long id);
    
    List<Goal> findByUserIdAndMonthAndYear(Long userId, Integer month, Integer year);

    Goal findById(Integer id);

    Goal save(Goal theGoal);

    void remove(Integer id);
}
