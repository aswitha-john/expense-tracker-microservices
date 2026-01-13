package com.expense.report_service.dto;

import java.math.BigDecimal;

public record CategorySummaryResponse(
        String categoryName,
        BigDecimal totalAmount
) { }
