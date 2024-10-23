package com.ntd.challenge.auth.v1.controllers;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.security.properties.JwtConfigurationProperties;
import com.ntd.challenge.auth.v1.test.utils.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private JwtConfigurationProperties jwtConfigurationProperties;

    @Test
    void login_ShouldReturnToken_WhenLoginRequestIsValid() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setId(1);
        user.setEmail(loginRequest.getEmail());

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        when(jwtConfigurationProperties.getSecret()).thenReturn("supersecretkeysupersecretkeysupersecretkey");
        when(jwtConfigurationProperties.getIssuer()).thenReturn("testIssuer");
        when(jwtConfigurationProperties.getExpiration()).thenReturn(60000L);

        // When & Then
        mockMvc.perform(post("/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenRequestIsInvalid() throws Exception {
        // Given
        LoginRequest invalidRequest = new LoginRequest();
        invalidRequest.setEmail("");

        // When & Then
        mockMvc.perform(post("/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}

