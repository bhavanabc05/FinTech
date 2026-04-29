package com.finance.fintech.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class GoalResponse {

    private String name;
    private double targetAmount;
    private double savedAmount;
    private double monthlyRequired;
}