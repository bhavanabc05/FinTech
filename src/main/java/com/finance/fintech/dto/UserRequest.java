package com.finance.fintech.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
}