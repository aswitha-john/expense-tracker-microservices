package com.expense.expense_service.controller;

import com.expense.expense_service.model.Expense;
import com.expense.expense_service.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(controllers = ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService expenseService;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    private Expense mockExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Lunch");
        expense.setAmount(BigDecimal.valueOf(250));
        expense.setExpenseDate(LocalDate.now());
        expense.setUsername("aswitha");
        return expense;
    }

    @Test
    @WithMockUser(username = "aswitha")
    void createExpense_success() throws Exception {

        Expense expense = mockExpense();

        Mockito.when(expenseService.create(Mockito.any(), Mockito.eq("aswitha")))
                .thenReturn(expense);

        mockMvc.perform(post("/expenses")
                        .with(csrf())
                        .header("X-User", "aswitha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message")
                        .value("Expense created successfully"))
                .andExpect(jsonPath("$.data.description").value("Lunch"));
    }

    @Test
    @WithMockUser(username = "aswitha")
    void getAllExpenses_success() throws Exception {

        Mockito.when(expenseService.getAll("aswitha"))
                .thenReturn(List.of(mockExpense()));

        mockMvc.perform(get("/expenses")
                        .header("X-User", "aswitha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Lunch"));
    }

    @Test
    @WithMockUser
    void getExpenseById_success() throws Exception {

        Mockito.when(expenseService.getById(1L))
                .thenReturn(mockExpense());

        mockMvc.perform(get("/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Lunch"));
    }

    @Test
    @WithMockUser
    void updateExpense_success() throws Exception {

        Expense updated = mockExpense();
        updated.setDescription("Dinner");

        Mockito.when(expenseService.update(Mockito.eq(1L), Mockito.any()))
                .thenReturn(updated);

        mockMvc.perform(put("/expenses/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Dinner"));
    }

    @Test
    @WithMockUser
    void deleteExpense_success() throws Exception {

        Expense expense = mockExpense();

        Mockito.when(expenseService.getById(1L)).thenReturn(expense);
        Mockito.doNothing().when(expenseService).delete(1L);

        mockMvc.perform(delete("/expenses/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Expense deleted successfully"));
    }
}

