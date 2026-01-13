package com.expense.report_service.controller;

import com.expense.report_service.dto.ApiResponse;
import com.expense.report_service.dto.CategorySummaryResponse;
import com.expense.report_service.dto.MonthlySummaryResponse;
import com.expense.report_service.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    // ===============================
    // Monthly Report
    // ===============================
    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<MonthlySummaryResponse>> monthlyReport(
            @RequestHeader("X-User") String username,
            @RequestParam int month,
            @RequestParam int year) {

        MonthlySummaryResponse response =
                service.getMonthlySummary(username, month, year);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Monthly expense summary generated successfully",
                        response
                )
        );
    }

    // ===============================
    // Category Summary
    // ===============================
    @GetMapping("/category-summary")
    public ResponseEntity<ApiResponse<List<CategorySummaryResponse>>> categorySummary(
            @RequestHeader("X-User") String username) {

        List<CategorySummaryResponse> response =
                service.getCategorySummary(username);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Category-wise expense summary generated successfully",
                        response
                )
        );
    }
}
