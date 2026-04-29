package com.finance.fintech.repository;

import com.finance.fintech.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findByUserId(int userId);
}