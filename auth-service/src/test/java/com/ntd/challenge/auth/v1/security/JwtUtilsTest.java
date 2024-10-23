package com.ntd.challenge.auth.v1.security;

import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.entities.enums.UserStatusEnum;
import com.ntd.challenge.auth.v1.security.JwtUtils;
import com.ntd.challenge.auth.v1.security.properties.JwtConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private JwtConfigurationProperties properties;

    @InjectMocks
    private JwtUtils jwtUtils;

    @Test
    void generateAccessToken_ShouldGenerateValidToken() {
        // Given
        User user = getMockUser();

        when(properties.getSecret()).thenReturn("supersecretkeysupersecretkeysupersecretkey");
        when(properties.getIssuer()).thenReturn("testIssuer");
        when(properties.getExpiration()).thenReturn(60000L);

        // When
        String token = jwtUtils.generateAccessToken(user);

        // Then
        assertNotNull(token);
        assertTrue(jwtUtils.validateAccessToken(token));
    }

    @Test
    void validateAccessToken_ShouldReturnTrue_WhenTokenIsValid() {
        // Given
        User user = getMockUser();

        when(properties.getSecret()).thenReturn("supersecretkeysupersecretkeysupersecretkey");
        when(properties.getIssuer()).thenReturn("testIssuer");
        when(properties.getExpiration()).thenReturn(60000L);

        String token = jwtUtils.generateAccessToken(user);

        // When
        boolean isValid = jwtUtils.validateAccessToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateAccessToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        // Given
        String invalidToken = "invalid.token";

        // When
        boolean isValid = jwtUtils.validateAccessToken(invalidToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void getUserFromToken_ShouldReturnUserDetails_WhenTokenIsValid() {
        // Given
        User user = getMockUser();

        when(properties.getSecret()).thenReturn("supersecretkeysupersecretkeysupersecretkey");
        when(properties.getIssuer()).thenReturn("testIssuer");
        when(properties.getExpiration()).thenReturn(60000L);

        String token = jwtUtils.generateAccessToken(user);

        // When
        UserDetails userDetails = jwtUtils.getUserFromToken(token);

        // Then
        assertNotNull(userDetails);
        assertEquals("test@test.com", userDetails.getUsername());
    }

    @Test
    void getUserFromToken_ShouldThrowException_WhenTokenIsInvalid() {
        // Given
        String invalidToken = "invalid.token";

        // When / Then
        assertThrows(Exception.class, () -> jwtUtils.getUserFromToken(invalidToken));
    }

    private User getMockUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setStatus(UserStatusEnum.ACTIVE);
        return user;
    }
}

