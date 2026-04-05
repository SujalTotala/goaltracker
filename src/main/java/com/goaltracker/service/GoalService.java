package com.goaltracker.service;

import com.goaltracker.model.Goal;
import com.goaltracker.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public Goal updateGoal(Long id, Goal newGoal) {
        Goal goal = goalRepository.findById(id).orElse(null);

        if (goal != null) {
            goal.setTitle(newGoal.getTitle());
            goal.setDescription(newGoal.getDescription());
            return goalRepository.save(goal);
        }

        return null;
    }
}