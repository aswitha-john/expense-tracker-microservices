package com.expense.expense_service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidInputExceptionTest {

    @Test
    void testExceptionMessage() {

        String message = "Invalid expense amount";

        InvalidInputException exception =
                new InvalidInputException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionIsRuntimeException() {

        InvalidInputException exception =
                new InvalidInputException("Invalid input");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionThrown() {

        assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException("Invalid category");
        });
    }
}
