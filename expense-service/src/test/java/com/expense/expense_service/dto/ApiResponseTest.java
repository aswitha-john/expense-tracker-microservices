package com.expense.expense_service.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testConstructorAndGetters() {

        LocalDateTime now = LocalDateTime.now();
        int status = 200;
        String message = "Success";
        String data = "Expense created";

        ApiResponse<String> response =
                new ApiResponse<>(now, status, message, data);

        assertEquals(now, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void testSetters() {

        ApiResponse<Integer> response =
                new ApiResponse<>(null, 0, null, null);

        LocalDateTime now = LocalDateTime.now();

        response.setTimestamp(now);
        response.setStatus(201);
        response.setMessage("Created");
        response.setData(100);

        assertEquals(now, response.getTimestamp());
        assertEquals(201, response.getStatus());
        assertEquals("Created", response.getMessage());
        assertEquals(100, response.getData());
    }

    @Test
    void testGenericTypeWithCustomObject() {

        class Dummy {
            private final String value;
            Dummy(String value) { this.value = value; }
            String getValue() { return value; }
        }

        Dummy dummy = new Dummy("test");

        ApiResponse<Dummy> response =
                new ApiResponse<>(
                        LocalDateTime.now(),
                        200,
                        "OK",
                        dummy
                );

        assertNotNull(response.getData());
        assertEquals("test", response.getData().getValue());
    }
}
