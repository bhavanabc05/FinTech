package com.finance.fintech.service;

import com.finance.fintech.entity.Budget;
import com.finance.fintech.dto.BudgetResponse;
import com.finance.fintech.dto.RecommendationResponse;
import com.finance.fintech.repository.BudgetRepository;
import com.finance.fintech.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<BudgetResponse> getBudgetStatus(int userId) {

        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<BudgetResponse> responseList = new ArrayList<>();

        for (Budget b : budgets) {

            double spent = transactionRepository.getCategoryExpense(
                    userId,
                    b.getCategory(),
                    b.getMonth(),
                    b.getYear()
            );

            String status;
            if (spent > b.getLimitAmount()) {
                status = "EXCEEDED";
            } else if (spent > 0.8 * b.getLimitAmount()) {
                status = "WARNING";
            } else {
                status = "SAFE";
            }

            responseList.add(new BudgetResponse(
                    b.getCategory(),
                    b.getLimitAmount(),
                    spent,
                    status
            ));
        }

        return responseList;
    }

    public List<RecommendationResponse> getRecommendations(int userId) {

        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<RecommendationResponse> recommendations = new ArrayList<>();

        for (Budget b : budgets) {

            double spent = transactionRepository.getCategoryExpense(
                    userId,
                    b.getCategory(),
                    b.getMonth(),
                    b.getYear()
            );

            double limit = b.getLimitAmount();

            if (spent > limit) {
                double excess = spent - limit;
                recommendations.add(new RecommendationResponse(
                        b.getCategory(),
                        "You exceeded your budget by " + excess + ". Reduce spending."
                ));
            } else if (spent > 0.8 * limit) {
                recommendations.add(new RecommendationResponse(
                        b.getCategory(),
                        "You are close to your budget limit. Be cautious."
                ));
            } else {
                recommendations.add(new RecommendationResponse(
                        b.getCategory(),
                        "Good job! You are within your budget."
                ));
            }
        }

        return recommendations;
    }
    
    public void deleteBudget(int id) {
        if (!budgetRepository.existsById(id)) {
            throw new RuntimeException("Budget not found");
        }
        budgetRepository.deleteById(id);
    }
}