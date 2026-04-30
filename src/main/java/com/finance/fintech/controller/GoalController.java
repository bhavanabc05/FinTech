package com.finance.fintech.controller;

import jakarta.validation.Valid;
import com.finance.fintech.entity.Goal;
import com.finance.fintech.dto.GoalResponse;
import com.finance.fintech.dto.ApiResponse;
import com.finance.fintech.service.GoalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // ADD GOAL
    @PostMapping
    public ApiResponse<Goal> addGoal(@Valid @RequestBody Goal goal) {

        Goal saved = goalService.addGoal(goal);

        return new ApiResponse<>(
                true,
                "Goal added successfully",
                saved
        );
    }

    // GET GOALS
    @GetMapping("/{userId}")
    public ApiResponse<List<GoalResponse>> getGoals(@PathVariable int userId) {

        List<GoalResponse> list = goalService.getGoals(userId);

        return new ApiResponse<>(
                true,
                "Goals fetched successfully",
                list
        );
    }
}