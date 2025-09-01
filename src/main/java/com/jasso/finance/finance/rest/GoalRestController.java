package com.jasso.finance.finance.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasso.finance.finance.entity.Goal;
import com.jasso.finance.finance.service.GoalService;
import com.jasso.finance.finance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GoalRestController {
    //create the field for the goal service
    private GoalService goalService;
    private ObjectMapper objectMapper;
    //inject it with constructor
    @Autowired
    public GoalRestController(GoalService theGoalService, ObjectMapper theObjectMapper) {
        goalService = theGoalService;
        objectMapper = theObjectMapper;
    }

    @GetMapping("/goals")
    public List<Goal> getGoals(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year){
        
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        if (month != null && year != null) {
            return goalService.findByUserIdAndMonthAndYear(authenticatedUserId.intValue(), month, year);
        }
        return goalService.findAll(authenticatedUserId.intValue());
    }

    @PostMapping("/goals")
    public Goal addGoal(@RequestBody Goal theGoal){
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        theGoal.setUser_id(authenticatedUserId.intValue());
        Goal newGoal = goalService.save(theGoal);
        return newGoal;
    }

    //add mapping for the PUT resquest, updating a goal
    @PutMapping("/goals")
    public Goal updateGoal(@RequestBody Goal theGoal) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Goal existingGoal = goalService.findById(theGoal.getId());
        if(existingGoal == null){
            throw new RuntimeException("Goal not found - " + theGoal.getId());
        }
        
        if(existingGoal.getUser_id() != authenticatedUserId.intValue()){
            throw new SecurityException("Unauthorized access to goal");
        }
        
        theGoal.setUser_id(authenticatedUserId.intValue());
        Goal newGoal = goalService.save(theGoal);
        return newGoal;
    }

    //add mapping for the PATCH method, updating specific part of an employee
    @PatchMapping("/goals/{goalId}")
    public Goal patchGoal(@PathVariable int goalId,
                                  @RequestBody Map<String, Object> patchPayload) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Goal tempGoal = goalService.findById(goalId);

        if (tempGoal == null){
            throw new RuntimeException("goal not found");
        }
        
        if(tempGoal.getUser_id() != authenticatedUserId.intValue()){
            throw new SecurityException("Unauthorized access to goal");
        }

        if (patchPayload.containsKey("id")){
            throw new RuntimeException("goal id not allowed in the payload");
        }
        
        if (patchPayload.containsKey("userId")){
            throw new RuntimeException("userId not allowed in the payload");
        }

        Goal patchedGoal = apply(patchPayload, tempGoal);

        Goal dbGoal = goalService.save(patchedGoal);
        return dbGoal;
    }

    @DeleteMapping("/goals/{goalId}")
    public Map<String, String> deleteGoal(@PathVariable int goalId) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Goal tempGoal = goalService.findById(goalId);

        if (tempGoal == null){
            throw new RuntimeException("this goal doesn't exist");
        }
        
        if(tempGoal.getUser_id() != authenticatedUserId.intValue()){
            throw new SecurityException("Unauthorized access to goal");
        }
        
        goalService.remove(goalId);

        return Map.of("message", "Goal: " + tempGoal.getTitle() + " Deleted from the database");
    }




    private Goal apply(Map<String, Object> patchPayload, Goal tempGoal){
        //Convert employee object to JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(tempGoal, ObjectNode.class);

        //convert payload map to a json node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        //merge the patch updates
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Goal.class);
    }
}
