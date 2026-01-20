package com.expense.expense_service.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testConstructorAndGetters() {

        LocalDateTime now = LocalDateTime.now();
        int status = 404;
        String error = "NOT_FOUND";
        String message = "Expense not found";
        String path = "/expenses/10";

        ErrorResponse response = new ErrorResponse(
                now,
                status,
                error,
                message,
                path
        );

        assertEquals(now, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void testSetters() {

        ErrorResponse response = new ErrorResponse(
                null,
                0,
                null,
                null,
                null
        );

        LocalDateTime now = LocalDateTime.now();

        response.setTimestamp(now);
        response.setStatus(400);
        response.setError("BAD_REQUEST");
        response.setMessage("Invalid input");
        response.setPath("/expenses");

        assertEquals(now, response.getTimestamp());
        assertEquals(400, response.getStatus());
        assertEquals("BAD_REQUEST", response.getError());
        assertEquals("Invalid input", response.getMessage());
        assertEquals("/expenses", response.getPath());
    }

    @Test
    void testNullValuesAllowed() {

        ErrorResponse response = new ErrorResponse(
                null,
                500,
                null,
                null,
                null
        );

        assertNull(response.getTimestamp());
        assertEquals(500, response.getStatus());
        assertNull(response.getError());
        assertNull(response.getMessage());
        assertNull(response.getPath());
    }
}
