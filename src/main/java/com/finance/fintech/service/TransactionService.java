package com.finance.fintech.service;

import com.finance.fintech.entity.Transaction;
import com.finance.fintech.entity.Budget;
import com.finance.fintech.repository.TransactionRepository;
import com.finance.fintech.repository.BudgetRepository;
import com.finance.fintech.dto.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public Map<String, Object> addTransaction(Transaction transaction) {

        Transaction saved = transactionRepository.save(transaction);

        String alert = checkBudgetAlert(saved);

        Map<String, Object> response = new HashMap<>();
        response.put("transaction", saved);
        response.put("alert", alert);

        return response;
    }

    public List<Transaction> getTransactions(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    public SummaryResponse getSummary(int userId) {
        double income = transactionRepository.getTotalIncome(userId);
        double expense = transactionRepository.getTotalExpense(userId);
        double savings = income - expense;
        String message;

        if (income == 0 && expense > 0) {
            message = "No income recorded. You are spending without earnings!";
        } else if (savings < 0) {
            message = "You are overspending!";
        } else if (savings == 0) {
            message = "No savings recorded.";
        } else {
            message = "Good job! You are saving money.";
        }

        return new SummaryResponse(income, expense, savings, message);
    }

    public Map<String, Double> getCategorySummary(int userId) {

        List<Object[]> results = transactionRepository.getCategoryWiseSummary(userId);

        Map<String, Double> summary = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double total = ((Number) row[1]).doubleValue();
            summary.put(category, total);
        }

        return summary;
    }

    public String checkBudgetAlert(Transaction transaction) {

        int userId = transaction.getUserId();
        String category = transaction.getCategory();

        int month = transaction.getDate().getMonthValue();
        int year = transaction.getDate().getYear();

        Budget budget = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYear(userId, category, month, year);

        if (budget == null) {
            return "No budget set";
        }

        double spent = transactionRepository.getCategoryExpense(userId, category, month, year);

        double limit = budget.getLimitAmount();

        if (spent >= limit) {
            return "Budget exceeded!";
        } else if (spent >= 0.8 * limit) {
            return "⚠️ 80% of budget used";
        } else {
            return "Within budget";
        }
    }

    public void deleteTransaction(int id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    // 🔹 FILTER METHODS
    public List<Transaction> filterByType(int userId, String type) {
        return transactionRepository.findByUserIdAndType(userId, type);
    }

    public List<Transaction> filterByCategory(int userId, String category) {
        return transactionRepository.findByUserIdAndCategory(userId, category);
    }

    public List<Transaction> filterByDateRange(int userId, String start, String end) {
        return transactionRepository.findByDateRange(
                userId,
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }
}