package com.expense.expense_service.service;

import com.expense.expense_service.exception.ResourceNotFoundException;
import com.expense.expense_service.model.Expense;
import com.expense.expense_service.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public Expense create(Expense expense, String username) {
        expense.setUsername(username);
        return repo.save(expense);
    }

    public List<Expense> getAll(String username) {
        return repo.findByUsername(username);
    }

    public Expense update(Long id, Expense updated) {
        Expense existing = getById(id);
        existing.setAmount(updated.getAmount());
        existing.setDescription(updated.getDescription());
        existing.setExpenseDate(updated.getExpenseDate());
        existing.setCategory(updated.getCategory());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.delete(getById(id));
    }

    public Expense getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense Not found"));
    }
}
