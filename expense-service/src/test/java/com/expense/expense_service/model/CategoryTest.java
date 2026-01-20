package com.expense.expense_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCategoryGettersAndSetters() {

        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        assertEquals(1L, category.getId());
        assertEquals("Food", category.getName());
    }

    @Test
    void testCategoryNameNotNull() {

        Category category = new Category();
        category.setName("Travel");

        assertNotNull(category.getName());
    }
}
