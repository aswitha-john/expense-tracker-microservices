package com.expense.auth_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void constructor_shouldSetFieldsCorrectly() {
        ApiResponse response = new ApiResponse("SUCCESS", "User registered successfully");

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("User registered successfully", response.getMessage());
    }

    @Test
    void setters_shouldUpdateValues() {
        ApiResponse response = new ApiResponse("SUCCESS", "Initial message");

        response.setStatus("FAILURE");
        response.setMessage("Something went wrong");

        assertEquals("FAILURE", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
    }

    @Test
    void getters_shouldNotReturnNull() {
        ApiResponse response = new ApiResponse("SUCCESS", "Valid message");

        assertNotNull(response.getStatus());
        assertNotNull(response.getMessage());
    }
}
