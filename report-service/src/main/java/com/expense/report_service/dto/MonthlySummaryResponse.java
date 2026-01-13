package com.expense.report_service.dto;

import java.math.BigDecimal;

public record MonthlySummaryResponse(
        int month,
        int year,
        BigDecimal totalAmount) {
}
