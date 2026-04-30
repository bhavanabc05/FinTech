package com.finance.fintech.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class SummaryResponse {
    private double totalIncome;
    private double totalExpense;
    private double savings;
    private String message;
}