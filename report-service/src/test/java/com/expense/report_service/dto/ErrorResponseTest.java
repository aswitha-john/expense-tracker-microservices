package com.expense.report_service.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void constructor_shouldInitializeAllFieldsCorrectly() {
        // given
        int status = 404;
        String error = "NOT_FOUND";
        String message = "Resource not found";
        String path = "/reports/monthly";

        // when
        ErrorResponse response =
                new ErrorResponse(status, error, message, path);

        // then
        assertNotNull(response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void timestamp_shouldBeCurrentTime() {
        // given
        LocalDateTime before = LocalDateTime.now();

        // when
        ErrorResponse response =
                new ErrorResponse(400, "BAD_REQUEST", "Invalid input", "/reports");

        LocalDateTime after = LocalDateTime.now();

        // then
        assertTrue(
                !response.getTimestamp().isBefore(before) &&
                        !response.getTimestamp().isAfter(after),
                "Timestamp should be between before and after time"
        );
    }

    @Test
    void fields_shouldNotBeNullExceptOptional() {
        // when
        ErrorResponse response =
                new ErrorResponse(500, "INTERNAL_SERVER_ERROR",
                        "Something went wrong", "/reports/category-summary");

        // then
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getError());
        assertNotNull(response.getMessage());
        assertNotNull(response.getPath());
    }
}
