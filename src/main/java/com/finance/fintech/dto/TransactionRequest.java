package com.finance.fintech.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionRequest {

    @NotNull
    private Integer userId;

    @NotBlank
    private String type;

    @Positive
    private Double amount;

    @NotBlank
    private String category;

    private String description;

    @NotNull
    private LocalDate date;
}