package com.expense.expense_service.controller;

import com.expense.expense_service.dto.ApiResponse;
import com.expense.expense_service.model.Expense;
import com.expense.expense_service.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseController.class);

    public ExpenseController(ExpenseService service) {

        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Expense>> create(
            @RequestBody Expense expense,
            @RequestHeader("X-User") String username) {

        Expense saved = service.create(expense, username);

        return ResponseEntity.ok(
                new ApiResponse<>(LocalDateTime.now(),
                        201,
                        "Expense created successfully",
                        saved
                )
        );
    }

    @GetMapping
    public List<Expense> getAll(@RequestHeader("X-User") String username){

        return service.getAll(username);
    }

    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id){

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense){

        return service.update(id, expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>> delete(@PathVariable Long id){

        Expense deleteExpense = service.getById(id);
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Expense deleted successfully",
                deleteExpense
        ));
    }
}
