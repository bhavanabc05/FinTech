package com.finance.fintech.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class BudgetResponse {

    private String category;
    private double limitAmount;
    private double spentAmount;
    private String status; // SAFE / WARNING / EXCEEDED
}