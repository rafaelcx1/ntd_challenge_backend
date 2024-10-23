package com.ntd.challenge.auth.v1.services.impl;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void login_ShouldReturnJwtToken_WhenCredentialsAreCorrect() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtUtils.generateAccessToken(user)).thenReturn("mocked-jwt-token");

        // When
        String token = authService.login(loginRequest);

        // Then
        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);

        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateAccessToken(user);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrong-password");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // When / Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());

        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateAccessToken(any(User.class));
    }
}

