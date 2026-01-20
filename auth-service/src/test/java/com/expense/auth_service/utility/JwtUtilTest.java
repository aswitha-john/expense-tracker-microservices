package com.expense.auth_service.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secret = "mySuperSecretKeyForJwtGeneration12345"; // must be >= 32 bytes
    private final long expiration = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret);
        // Manually set expiration since @Value won't work in unit test
        // We'll simulate using reflection
        try {
            java.lang.reflect.Field expirationField = JwtUtil.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.setLong(jwtUtil, expiration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateToken_shouldReturnNonNullToken() {
        String token = jwtUtil.generateToken("john_doe", "USER");
        assertNotNull(token, "JWT token should not be null");
    }

    @Test
    void generateToken_shouldContainUsernameAndRoleClaims() {
        String username = "john_doe";
        String role = "ADMIN";

        String token = jwtUtil.generateToken(username, role);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject(), "Username claim should match");
        assertEquals(role, claims.get("role"), "Role claim should match");
    }

    @Test
    void generateToken_shouldSetExpiration() {
        String token = jwtUtil.generateToken("john_doe", "USER");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertTrue(claims.getExpiration().after(new Date()), "Token expiration should be in the future");
    }
}
