package com.finance.fintech.repository;

import com.finance.fintech.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserId(int userId);

    // 🔹 FILTERS
    List<Transaction> findByUserIdAndType(int userId, String type);

    List<Transaction> findByUserIdAndCategory(int userId, String category);

    @Query("""
        SELECT t FROM Transaction t
        WHERE t.userId = :userId
        AND t.date BETWEEN :startDate AND :endDate
    """)
    List<Transaction> findByDateRange(
            @Param("userId") int userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.userId = :userId AND t.type = 'income'")
    double getTotalIncome(@Param("userId") int userId);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.userId = :userId AND t.type = 'expense'")
    double getTotalExpense(@Param("userId") int userId);

    @Query("""
        SELECT COALESCE(SUM(t.amount),0)
        FROM Transaction t
        WHERE t.userId = :userId
        AND t.type = 'expense'
        AND t.category = :category
        AND MONTH(t.date) = :month
        AND YEAR(t.date) = :year
    """)
    double getCategoryExpense(
            @Param("userId") int userId,
            @Param("category") String category,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("""
        SELECT t.category, SUM(t.amount)
        FROM Transaction t
        WHERE t.userId = :userId
        AND t.type = 'expense'
        GROUP BY t.category
    """)
    List<Object[]> getCategoryWiseSummary(@Param("userId") int userId);
}