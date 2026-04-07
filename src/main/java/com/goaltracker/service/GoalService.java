package com.goaltracker.service;

import com.goaltracker.model.Goal;
import com.goaltracker.repository.GoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    // ✅ SAVE (streak + badge logic)
    public Goal saveGoal(Goal goal) {

        LocalDate today = LocalDate.now();

        if (goal.getId() != null) {
            Goal existing = goalRepository.findById(goal.getId()).orElse(null);

            if (existing != null) {
                LocalDate lastDate = existing.getLastUpdated();

                if (lastDate != null) {
                    if (lastDate.plusDays(1).equals(today)) {
                        // continue streak
                        goal.setStreak(existing.getStreak() + 1);
                    } else if (lastDate.equals(today)) {
                        // same day → no change
                        goal.setStreak(existing.getStreak());
                    } else {
                        // missed → reset
                        goal.setStreak(1);
                    }
                } else {
                    goal.setStreak(1);
                }
            }
        } else {
            // new goal
            goal.setStreak(1);
        }

        goal.setLastUpdated(today);

        // 🏆 BADGE LOGIC
        if (goal.getStreak() >= 30) {
            goal.setBadge("🏆 Champion");
        } else if (goal.getStreak() >= 7) {
            goal.setBadge("💪 Consistent");
        } else if (goal.getStreak() >= 3) {
            goal.setBadge("🔥 Getting Started");
        } else {
            goal.setBadge("🌱 Beginner");
        }

        return goalRepository.save(goal);
    }

    // ✅ AUTO RESET STREAK
    public void checkAndResetStreaks() {

        LocalDate today = LocalDate.now();
        List<Goal> goals = goalRepository.findAll();

        for (Goal g : goals) {

            if (g.getLastUpdated() != null) {

                if (!g.getLastUpdated().equals(today) &&
                        !g.getLastUpdated().plusDays(1).equals(today)) {

                    g.setStreak(0);
                    g.setBadge("🌱 Beginner"); // reset badge
                    goalRepository.save(g);
                }
            }
        }
    }

    // ✅ GET ALL
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    // ✅ GET BY ID
    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    // ✅ DELETE
    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    // ✅ UPDATE
    public Goal updateGoal(Long id, Goal goal) {
        goal.setId(id);
        return saveGoal(goal);
    }
}