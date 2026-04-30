package com.finance.fintech.controller;

import com.finance.fintech.dto.TransactionRequest;
import com.finance.fintech.entity.Transaction;
import com.finance.fintech.service.TransactionService;
import com.finance.fintech.dto.SummaryResponse;
import com.finance.fintech.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ApiResponse<Map<String, Object>> addTransaction(@Valid @RequestBody TransactionRequest request) {
        Map<String, Object> result = transactionService.addTransaction(request);
        return new ApiResponse<>(true, "Transaction added", result);
    }

    @GetMapping("/{userId}")
    public List<Transaction> getTransactions(@PathVariable int userId) {
        return transactionService.getTransactions(userId);
    }

    @GetMapping("/summary/{userId}")
    public SummaryResponse getSummary(@PathVariable int userId) {
        return transactionService.getSummary(userId);
    }

    @GetMapping("/category-summary/{userId}")
    public Map<String, Double> getCategorySummary(@PathVariable int userId) {
        return transactionService.getCategorySummary(userId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return new ApiResponse<>(true, "Transaction deleted successfully", null);
    }

    // 🔹 FILTER ENDPOINTS

    @GetMapping("/filter/type")
    public List<Transaction> filterByType(
            @RequestParam int userId,
            @RequestParam String type
    ) {
        return transactionService.filterByType(userId, type);
    }

    @GetMapping("/filter/category")
    public List<Transaction> filterByCategory(
            @RequestParam int userId,
            @RequestParam String category
    ) {
        return transactionService.filterByCategory(userId, category);
    }

    @GetMapping("/filter/date")
    public List<Transaction> filterByDate(
            @RequestParam int userId,
            @RequestParam String start,
            @RequestParam String end
    ) {
        return transactionService.filterByDateRange(userId, start, end);
    }
}