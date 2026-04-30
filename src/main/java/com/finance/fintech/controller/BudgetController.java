package com.finance.fintech.controller;

import jakarta.validation.Valid;
import com.finance.fintech.entity.Budget;
import com.finance.fintech.dto.BudgetResponse;
import com.finance.fintech.dto.RecommendationResponse;
import com.finance.fintech.dto.ApiResponse;
import com.finance.fintech.service.BudgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    //  ADD BUDGET
    @PostMapping
    public ApiResponse<Budget> addBudget(@Valid @RequestBody Budget budget) {
        Budget saved = budgetService.addBudget(budget);

        return new ApiResponse<>(
                true,
                "Budget added successfully",
                saved
        );
    }

    // GET BUDGET STATUS
    @GetMapping("/{userId}")
    public ApiResponse<List<BudgetResponse>> getBudgetStatus(@PathVariable int userId) {
        List<BudgetResponse> list = budgetService.getBudgetStatus(userId);

        return new ApiResponse<>(
                true,
                "Budget status fetched",
                list
        );
    }

    // GET RECOMMENDATIONS
    @GetMapping("/recommendations/{userId}")
    public ApiResponse<List<RecommendationResponse>> getRecommendations(@PathVariable int userId) {
        List<RecommendationResponse> list = budgetService.getRecommendations(userId);

        return new ApiResponse<>(
                true,
                "Recommendations fetched",
                list
        );
    }

    // DELETE BUDGET
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBudget(@PathVariable int id) {
        budgetService.deleteBudget(id);

        return new ApiResponse<>(
                true,
                "Budget deleted successfully",
                null
        );
    }
}