package com.finance.fintech.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalRequest {

    @NotNull
    private Integer userId;

    @NotBlank(message = "Goal name is required")
    private String name;

    @Positive(message = "Target must be positive")
    private double targetAmount;

    @PositiveOrZero(message = "Saved amount cannot be negative")
    private double savedAmount;

    @Future(message = "Deadline must be in future")
    private LocalDate deadline;
}