package com.ntd.challenge.auth.v1.services.impl;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.exceptions.types.ExistentUserException;
import com.ntd.challenge.auth.v1.repositories.UserRepository;
import com.ntd.challenge.auth.v1.utils.AuthContextUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userService.findByEmail(email);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = userService.findByEmail(email);

        // Then
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        // Given
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When / Then
        assertThrows(ExistentUserException.class, () -> {
            userService.registerUser(request);
        });

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_ShouldSaveUser_WhenUserDoesNotExist() {
        // Given
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // When
        userService.registerUser(request);

        // Then
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void get_ShouldReturnLoggedUser_WhenUserIsAuthenticated() {
        // Given
        User loggedUser = new User();
        loggedUser.setId(1);
        loggedUser.setEmail("test@example.com");

        try (MockedStatic<AuthContextUtils> mockedAuthContext = mockStatic(AuthContextUtils.class)) {
            mockedAuthContext.when(AuthContextUtils::getLoggedUser).thenReturn(loggedUser);
            when(userRepository.getReferenceById(loggedUser.getId())).thenReturn(loggedUser);

            // When
            User result = userService.get();

            // Then
            assertNotNull(result);
            assertEquals(loggedUser.getId(), result.getId());
            assertEquals(loggedUser.getEmail(), result.getEmail());
            verify(userRepository, times(1)).getReferenceById(loggedUser.getId());
        }
    }

    @Test
    void get_ShouldThrowAccessDeniedException_WhenUserIsNotAuthenticated() {
        try (MockedStatic<AuthContextUtils> mockedAuthContext = mockStatic(AuthContextUtils.class)) {
            mockedAuthContext.when(AuthContextUtils::getLoggedUser).thenReturn(null);

            // When / Then
            assertThrows(AccessDeniedException.class, () -> {
                userService.get();
            });

            verify(userRepository, never()).getReferenceById(anyInt());
        }
    }
}

