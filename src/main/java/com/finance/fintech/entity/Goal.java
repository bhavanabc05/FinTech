package com.finance.fintech.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Data
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalId;
    private int userId;
    private String name;
    private double targetAmount;
    private double savedAmount;
    private LocalDate deadline;
}