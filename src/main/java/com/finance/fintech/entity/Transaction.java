package com.finance.fintech.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private int userId;
    private String type; // income or expense
    private double amount;
    private String category;
    private String description;
    private LocalDate date;
}