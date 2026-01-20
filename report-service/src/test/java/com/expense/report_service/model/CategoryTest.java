package com.expense.report_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldSetAndGetId() {
        // given
        Category category = new Category();
        Long id = 1L;

        // when
        category.setId(id);

        // then
        assertEquals(id, category.getId());
    }

    @Test
    void shouldSetAndGetName() {
        // given
        Category category = new Category();
        String name = "Food";

        // when
        category.setName(name);

        // then
        assertEquals(name, category.getName());
    }

    @Test
    void shouldCreateValidCategoryObject() {
        // given
        Category category = new Category();

        // when
        category.setId(10L);
        category.setName("Transport");

        // then
        assertNotNull(category);
        assertEquals(10L, category.getId());
        assertEquals("Transport", category.getName());
    }

    @Test
    void nameShouldNotBeNullAfterSetting() {
        // given
        Category category = new Category();

        // when
        category.setName("Shopping");

        // then
        assertNotNull(category.getName());
    }
}
