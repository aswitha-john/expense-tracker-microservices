package com.expense.report_service.repository;

import com.expense.report_service.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUsernameAndExpenseDateBetween(
            String username,
            LocalDate start,
            LocalDate end
    );

    List<Expense> findByUsername(String username);
}
