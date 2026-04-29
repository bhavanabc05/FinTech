package com.finance.fintech.controller;

import com.finance.fintech.entity.Goal;
import com.finance.fintech.dto.GoalResponse;
import com.finance.fintech.service.GoalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public Goal addGoal(@RequestBody Goal goal) {
        return goalService.addGoal(goal);
    }

    @GetMapping("/{userId}")
    public List<GoalResponse> getGoals(@PathVariable int userId) {
        return goalService.getGoals(userId);
    }
}