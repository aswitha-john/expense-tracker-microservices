package com.expense.report_service.service;

import com.expense.report_service.dto.CategorySummaryResponse;
import com.expense.report_service.dto.MonthlySummaryResponse;
import com.expense.report_service.model.Expense;
import com.expense.report_service.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    // ===============================
    // Monthly Summary
    // ===============================
    public MonthlySummaryResponse getMonthlySummary(
            String username, int month, int year) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        BigDecimal totalAmount = repository
                .findByUsernameAndExpenseDateBetween(username, startDate, endDate)
                .stream()
                .map(Expense::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MonthlySummaryResponse(month, year, totalAmount);
    }

    // ===============================
    // Category Summary
    // ===============================
    public List<CategorySummaryResponse> getCategorySummary(String username) {

        Map<String, BigDecimal> summaryMap = repository
                .findByUsername(username)
                .stream()
                .filter(expense -> expense.getCategory() != null)
                .collect(Collectors.groupingBy(
                        expense -> expense.getCategory().getName(),
                        Collectors.mapping(
                                Expense::getAmount,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        BigDecimal::add
                                )
                        )
                ));

        return summaryMap.entrySet()
                .stream()
                .map(entry ->
                        new CategorySummaryResponse(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }
}
