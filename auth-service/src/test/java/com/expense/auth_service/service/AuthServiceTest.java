package com.expense.auth_service.service;

import com.expense.auth_service.dto.LoginRequest;
import com.expense.auth_service.dto.RegisterRequest;
import com.expense.auth_service.exception.InvalidCredentialsException;
import com.expense.auth_service.exception.UserAlreadyExistsException;
import com.expense.auth_service.model.User;
import com.expense.auth_service.repository.UserRepository;
import com.expense.auth_service.utility.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // =========================
    // Register Tests
    // =========================

    @Test
    void register_shouldSaveUser_whenUserDoesNotExist() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("aswitha");
        request.setPassword("password123");

        when(userRepository.findByUsername("aswitha"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        authService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenUserAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("aswitha");
        request.setPassword("password123");

        when(userRepository.findByUsername("aswitha"))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class,
                () -> authService.register(request));

        verify(userRepository, never()).save(any());
    }

    // =========================
    // Login Tests
    // =========================

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {

        LoginRequest request = new LoginRequest();
        request.setUsername("aswitha");
        request.setPassword("password123");

        User user = new User();
        user.setUsername("aswitha");
        user.setPassword("encoded-password");
        user.setRole("USER");

        when(userRepository.findByUsername("aswitha"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password123", "encoded-password"))
                .thenReturn(true);

        when(jwtUtil.generateToken("aswitha", "USER"))
                .thenReturn("mock-jwt-token");

        String token = authService.login(request);

        assertEquals("mock-jwt-token", token);
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {

        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("password");

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(request));
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid() {

        LoginRequest request = new LoginRequest();
        request.setUsername("aswitha");
        request.setPassword("wrong-password");

        User user = new User();
        user.setUsername("aswitha");
        user.setPassword("encoded-password");

        when(userRepository.findByUsername("aswitha"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(request));
    }
}
