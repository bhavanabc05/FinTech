package com.finance.fintech.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budgets")
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int budgetId;
    private int userId;
    private String category;
    private double limitAmount;
    private int month;  // 1-12
    private int year;   // e.g., 2026
}