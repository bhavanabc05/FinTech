package com.finance.fintech.service;

import com.finance.fintech.dto.TransactionRequest;
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

    // ADD TRANSACTION (WITH VALIDATION + ALERT)
    public Map<String, Object> addTransaction(TransactionRequest request) {

        // TYPE VALIDATION
        if (!request.getType().equalsIgnoreCase("income") &&
                !request.getType().equalsIgnoreCase("expense")) {
            throw new RuntimeException("Invalid transaction type. Must be 'income' or 'expense'");
        }

        // DATE SAFETY CHECK
        if (request.getDate() == null) {
            throw new RuntimeException("Transaction date cannot be null");
        }

        // DTO → ENTITY MAPPING
        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setType(request.getType().toLowerCase());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());

        Transaction saved = transactionRepository.save(transaction);

        // BUDGET ALERT
        String alert = checkBudgetAlert(saved);

        // RESPONSE
        Map<String, Object> response = new HashMap<>();
        response.put("transaction", saved);
        response.put("alert", alert);

        return response;
    }

    // GET ALL TRANSACTIONS
    public List<Transaction> getTransactions(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    // SUMMARY (WITH RECOMMENDATION)
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
            double savingRate = (income == 0) ? 0 : (savings / income) * 100;

            if (savingRate < 20) {
                message = "Try saving more. Current savings: " + (int) savingRate + "%";
            } else {
                message = "Good job! You are saving " + (int) savingRate + "% of your income.";
            }
        }

        return new SummaryResponse(income, expense, savings, message);
    }

    // CATEGORY SUMMARY
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

    // BUDGET ALERT (DYNAMIC)
    public String checkBudgetAlert(Transaction transaction) {

        if (transaction.getDate() == null) {
            return "Invalid date";
        }

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

        double percent = (spent / limit) * 100;

        if (spent >= limit) {
            return "Budget exceeded! (" + (int) percent + "% used)";
        } else if (spent >= 0.8 * limit) {
            return "⚠️ Warning: " + (int) percent + "% of budget used";
        } else {
            return "Within budget (" + (int) percent + "% used)";
        }
    }

    // DELETE TRANSACTION
    public void deleteTransaction(int id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction with ID " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }

    // FILTER METHODS

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