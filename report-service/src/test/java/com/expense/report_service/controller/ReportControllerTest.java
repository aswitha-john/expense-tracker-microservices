package com.expense.report_service.controller;

import com.expense.report_service.dto.ApiResponse;
import com.expense.report_service.dto.CategorySummaryResponse;
import com.expense.report_service.dto.MonthlySummaryResponse;
import com.expense.report_service.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private ReportService service;

    @InjectMocks
    private ReportController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ===============================
    // Test for monthlyReport endpoint
    // ===============================
    @Test
    void monthlyReport_shouldReturnMonthlySummary() {
        String username = "john_doe";
        int month = 1;
        int year = 2026;

        MonthlySummaryResponse mockResponse = new MonthlySummaryResponse(month, year, BigDecimal.valueOf(5000));
        when(service.getMonthlySummary(username, month, year)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<MonthlySummaryResponse>> response = controller.monthlyReport(username, month, year);

        assertEquals(200, response.getBody().getStatus());
        assertEquals("Monthly expense summary generated successfully", response.getBody().getMessage());
        assertEquals(mockResponse, response.getBody().getData());

        verify(service, times(1)).getMonthlySummary(username, month, year);
    }

    // ===============================
    // Test for categorySummary endpoint
    // ===============================
    @Test
    void categorySummary_shouldReturnCategorySummaryList() {
        String username = "john_doe";

        List<CategorySummaryResponse> mockResponse = List.of(
                new CategorySummaryResponse("Food", BigDecimal.valueOf(2000)),
                new CategorySummaryResponse("Transport", BigDecimal.valueOf(1000))
        );

        when(service.getCategorySummary(username)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<List<CategorySummaryResponse>>> response = controller.categorySummary(username);

        assertEquals(200, response.getBody().getStatus());
        assertEquals("Category-wise expense summary generated successfully", response.getBody().getMessage());
        assertEquals(mockResponse, response.getBody().getData());

        verify(service, times(1)).getCategorySummary(username);
    }
}
