package com.finance.fintech.controller;

import jakarta.validation.Valid;
import com.finance.fintech.entity.User;
import com.finance.fintech.service.UserService;
import com.finance.fintech.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody User user) {

        User savedUser = userService.registerUser(user);

        return new ApiResponse<>(
                true,
                "User registered successfully",
                savedUser
        );
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody User user) {

        User loggedIn = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );

        return new ApiResponse<>(
                true,
                "Login successful",
                loggedIn
        );
    }
}