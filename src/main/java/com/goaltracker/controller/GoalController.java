package com.goaltracker.controller;

import com.goaltracker.model.Goal;
import com.goaltracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/add")
    public Goal addGoal(@RequestBody Goal goal) {
        return goalService.saveGoal(goal);
    }

    @GetMapping("/goals")
    public List<Goal> getGoals() {
        return goalService.getAllGoals();
    }

    @GetMapping("/goals/{id}")
    public Goal getGoal(@PathVariable Long id) {
        return goalService.getGoalById(id);
    }

    @DeleteMapping("/goals/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "Deleted";
    }

    @PutMapping("/goals/{id}")
    public Goal updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        return goalService.updateGoal(id, goal);
    }
}