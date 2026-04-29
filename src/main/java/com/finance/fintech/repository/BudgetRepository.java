package com.finance.fintech.repository;

import com.finance.fintech.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findByUserId(int userId);
}