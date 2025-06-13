package com.jasso.finance.finance.dao;
import com.jasso.finance.finance.entity.Goal;

import java.util.List;

public interface GoalDAO {
    List<Goal> findAll();

    Goal findById(Integer id);

    void save(Goal theGoal);

    void remove(Integer id);
}
