package com.goaltracker.controller;

import com.goaltracker.model.Goal;
import com.goaltracker.service.GoalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")  // better practice (base URL)
public class GoalController {

    @Autowired
    private GoalService goalService;

    // ✅ CREATE
    @PostMapping
    public Goal addGoal(@RequestBody Goal goal) {
        return goalService.saveGoal(goal);
    }

    // ✅ READ ALL
    @GetMapping
    public List<Goal> getGoals() {
        goalService.checkAndResetStreaks(); // streak logic
        return goalService.getAllGoals();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Goal updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        Goal existing = goalService.getGoalById(id);

        existing.setTitle(goal.getTitle());
        existing.setCompleted(goal.isCompleted()); // ✅ NEW

        return goalService.saveGoal(existing);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "Goal Deleted Successfully";
    }
}