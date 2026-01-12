package com.expense.expense_service.repository;

import com.expense.expense_service.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUsername(String username);

    List<Expense> findByCategory_Name(String category);

    List<Expense> findByExpenseDateBetween(LocalDate start, LocalDate end);

    List<Expense> findByAmountBetween(BigDecimal min, BigDecimal max);
}
