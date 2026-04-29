package com.finance.fintech.service;

import com.finance.fintech.entity.Budget;
import com.finance.fintech.dto.BudgetResponse;
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
}