package com.finance.fintech.controller;

import jakarta.validation.Valid;
import com.finance.fintech.entity.User;
import com.finance.fintech.service.UserService;
import com.finance.fintech.dto.ApiResponse;
import com.finance.fintech.dto.UserResponse;
import com.finance.fintech.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody User user) {

        User savedUser = userService.registerUser(user);

        UserResponse response = new UserResponse(
                savedUser.getUserId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return new ApiResponse<>(
                true,
                "User registered successfully",
                response
        );
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        User loggedIn = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        UserResponse response = new UserResponse(
                loggedIn.getUserId(),
                loggedIn.getName(),
                loggedIn.getEmail()
        );

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }
}