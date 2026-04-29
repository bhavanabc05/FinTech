package com.finance.fintech.controller;

import com.finance.fintech.entity.Budget;
import com.finance.fintech.dto.BudgetResponse;
import com.finance.fintech.service.BudgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public Budget addBudget(@RequestBody Budget budget) {
        return budgetService.addBudget(budget);
    }

    @GetMapping("/{userId}")
    public List<BudgetResponse> getBudgetStatus(@PathVariable int userId) {
        return budgetService.getBudgetStatus(userId);
    }
}