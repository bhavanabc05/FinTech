package com.finance.fintech.service;

import com.finance.fintech.entity.Goal;
import com.finance.fintech.dto.GoalResponse;
import com.finance.fintech.repository.GoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal addGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<GoalResponse> getGoals(int userId) {

        List<Goal> goals = goalRepository.findByUserId(userId);
        List<GoalResponse> responseList = new ArrayList<>();

        for (Goal g : goals) {

            long months = ChronoUnit.MONTHS.between(
                    LocalDate.now(),
                    g.getDeadline()
            );

            if (months <= 0) months = 1;

            double remaining = g.getTargetAmount() - g.getSavedAmount();
            double monthlyRequired = remaining / months;

            responseList.add(new GoalResponse(
                    g.getName(),
                    g.getTargetAmount(),
                    g.getSavedAmount(),
                    monthlyRequired
            ));
        }

        return responseList;
    }
}