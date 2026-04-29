package com.finance.fintech.controller;

import com.finance.fintech.entity.Transaction;
import com.finance.fintech.service.TransactionService;
import com.finance.fintech.dto.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    @GetMapping("/{userId}")
    public List<Transaction> getTransactions(@PathVariable int userId) {
        return transactionService.getTransactions(userId);
    }

    @GetMapping("/summary/{userId}")
    public SummaryResponse getSummary(@PathVariable int userId) {
        return transactionService.getSummary(userId);
    }
}