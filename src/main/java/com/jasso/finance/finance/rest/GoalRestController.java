package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.entity.Goal;
import com.jasso.finance.finance.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalRestController {
    //create the field for the goal service
    private GoalService goalService;

    //inject it with constructor
    @Autowired
    public GoalRestController(GoalService theGoalService) {
        goalService = theGoalService;
    }

    @GetMapping("/goals")
    public List<Goal> getGoals(){
        return goalService.findAll();
    }
}
