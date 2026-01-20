package com.expense.expense_service.service;

import com.expense.expense_service.exception.ResourceNotFoundException;
import com.expense.expense_service.model.Expense;
import com.expense.expense_service.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;

    @BeforeEach
    void setup() {
        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Lunch");
        expense.setAmount(BigDecimal.valueOf(250));
        expense.setExpenseDate(LocalDate.now());
        expense.setUsername("aswitha");
    }

    @Test
    void createExpense_success() {

        when(expenseRepository.save(any(Expense.class)))
                .thenReturn(expense);

        Expense saved = expenseService.create(expense, "aswitha");

        assertNotNull(saved);
        assertEquals("aswitha", saved.getUsername());
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void getAllExpenses_success() {

        when(expenseRepository.findByUsername("aswitha"))
                .thenReturn(List.of(expense));

        List<Expense> result = expenseService.getAll("aswitha");

        assertEquals(1, result.size());
        assertEquals("Lunch", result.get(0).getDescription());
        verify(expenseRepository).findByUsername("aswitha");
    }

    @Test
    void getExpenseById_success() {

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        Expense result = expenseService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getExpenseById_notFound() {

        when(expenseRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.getById(99L));
    }

    @Test
    void updateExpense_success() {

        Expense updated = new Expense();
        updated.setDescription("Dinner");
        updated.setAmount(BigDecimal.valueOf(400));
        updated.setExpenseDate(LocalDate.now());

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        when(expenseRepository.save(any(Expense.class)))
                .thenReturn(expense);

        Expense result = expenseService.update(1L, updated);

        assertEquals("Dinner", result.getDescription());
        assertEquals(BigDecimal.valueOf(400), result.getAmount());
        verify(expenseRepository).save(expense);
    }

    @Test
    void updateExpense_notFound() {

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.update(1L, expense));
    }

    @Test
    void deleteExpense_success() {

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.of(expense));

        doNothing().when(expenseRepository).delete(expense);

        expenseService.delete(1L);

        verify(expenseRepository, times(1)).delete(expense);
    }

    @Test
    void deleteExpense_notFound() {

        when(expenseRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.delete(1L));
    }
}
