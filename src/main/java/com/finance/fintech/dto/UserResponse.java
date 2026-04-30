package com.finance.fintech.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class UserResponse {

    private int userId;
    private String name;
    private String email;
}