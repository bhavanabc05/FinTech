package com.finance.fintech.service;

import com.finance.fintech.entity.Transaction;
import com.finance.fintech.repository.TransactionRepository;
import com.finance.fintech.dto.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(int userId) {
        return transactionRepository.findByUserId(userId);
    }
    public SummaryResponse getSummary(int userId) {
        double income = transactionRepository.getTotalIncome(userId);
        double expense = transactionRepository.getTotalExpense(userId);
        double savings = income - expense;
        return new SummaryResponse(income, expense, savings);
    }
}