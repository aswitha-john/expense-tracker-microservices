package com.expense.auth_service.controller;

import com.expense.auth_service.dto.LoginRequest;
import com.expense.auth_service.dto.RegisterRequest;
import com.expense.auth_service.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    /* ---------------- REGISTER TEST ---------------- */

    @Test
    void register_shouldReturnSuccessResponse() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");

        Mockito.doNothing().when(authService).register(Mockito.any());

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    /* ---------------- LOGIN TEST ---------------- */

    @Test
    void login_shouldReturnJwtToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("password123");

        Mockito.when(authService.login(Mockito.any()))
                .thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }
}
