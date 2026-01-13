package com.expense.report_service.controller;

import com.expense.report_service.dto.CategorySummaryResponse;
import com.expense.report_service.dto.MonthlySummaryResponse;
import com.expense.report_service.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {


    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/monthly")
    public MonthlySummaryResponse monthlyReport(
            @RequestHeader("X-User") String username,
            @RequestParam int month,
            @RequestParam int year) {

        return service.getMonthlySummary(username, month, year);
    }

    @GetMapping("/category-summary")
    public List<CategorySummaryResponse> categorySummary(
            @RequestHeader("X-User") String username) {

        return service.getCategorySummary(username);
    }

}
