package com.jasso.finance.finance.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasso.finance.finance.entity.Goal;
import com.jasso.finance.finance.service.GoalService;
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
    public List<Goal> getGoals(){
        return goalService.findAll();
    }

    @PostMapping("/goals")
    public Goal addGoal(@RequestBody Goal theGoal){
        Goal newGoal = goalService.save(theGoal);
        return newGoal;
    }

    //add mapping for the PUT resquest, updating a goal
    @PutMapping("/goals")
    public Goal updateEmployee(@RequestBody Goal theGoal) {
        Goal newGoal = goalService.save(theGoal);

        return newGoal;
    }

    //add mapping for the PATCH method, updating specific part of an employee
    @PatchMapping("/goals/{goalId}")
    public Goal patchEmployee(@PathVariable int goalId,
                                  @RequestBody Map<String, Object> patchPayload) {
        Goal tempGoal = goalService.findById(goalId);

        if (tempGoal == null){
            throw new RuntimeException("goal not found");
        }

        if (patchPayload.containsKey("id")){
            throw new RuntimeException("employee id not allowed in the payload");
        }

        Goal patchedGoal = apply(patchPayload, tempGoal);

        Goal dbGoal = goalService.save(patchedGoal);
        return dbGoal;
    }

    @DeleteMapping("/goals/{goalId}")
    public Map<String, String> deleteGoal(@PathVariable int goalId) {
        Goal tempGoal = goalService.findById(goalId);

        if (tempGoal == null){
            throw new RuntimeException("this goal doesn't exist");
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
