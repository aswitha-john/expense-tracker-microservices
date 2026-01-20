package com.expense.report_service.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void shouldSetAndGetId() {
        Expense expense = new Expense();
        expense.setId(1L);

        assertEquals(1L, expense.getId());
    }

    @Test
    void shouldSetAndGetDescription() {
        Expense expense = new Expense();
        expense.setDescription("Lunch expense");

        assertEquals("Lunch expense", expense.getDescription());
    }

    @Test
    void shouldSetAndGetAmount() {
        Expense expense = new Expense();
        BigDecimal amount = new BigDecimal("250.50");

        expense.setAmount(amount);

        assertEquals(amount, expense.getAmount());
    }

    @Test
    void shouldSetAndGetExpenseDate() {
        Expense expense = new Expense();
        LocalDate date = LocalDate.of(2026, 1, 10);

        expense.setExpenseDate(date);

        assertEquals(date, expense.getExpenseDate());
    }

    @Test
    void shouldSetAndGetCategory() {
        Expense expense = new Expense();
        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        expense.setCategory(category);

        assertNotNull(expense.getCategory());
        assertEquals("Food", expense.getCategory().getName());
    }

    @Test
    void shouldSetAndGetUsername() {
        Expense expense = new Expense();
        expense.setUsername("aswitha");

        assertEquals("aswitha", expense.getUsername());
    }

    @Test
    void shouldCreateValidExpenseObject() {
        Expense expense = new Expense();

        expense.setId(10L);
        expense.setDescription("Grocery shopping");
        expense.setAmount(new BigDecimal("1200.00"));
        expense.setExpenseDate(LocalDate.now());
        expense.setUsername("test_user");

        Category category = new Category();
        category.setName("Groceries");
        expense.setCategory(category);

        assertNotNull(expense);
        assertEquals("Grocery shopping", expense.getDescription());
        assertEquals(new BigDecimal("1200.00"), expense.getAmount());
        assertEquals("Groceries", expense.getCategory().getName());
        assertEquals("test_user", expense.getUsername());
    }

    @Test
    void amountShouldNotBeNullWhenSet() {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.TEN);

        assertNotNull(expense.getAmount());
    }

    @Test
    void expenseDateShouldNotBeNullWhenSet() {
        Expense expense = new Expense();
        expense.setExpenseDate(LocalDate.now());

        assertNotNull(expense.getExpenseDate());
    }
}
