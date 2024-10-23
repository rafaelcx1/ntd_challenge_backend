package com.ntd.challenge.auth.v1.security.services.impl;

import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.entities.enums.UserStatusEnum;
import com.ntd.challenge.auth.v1.repositories.UserRepository;
import com.ntd.challenge.auth.v1.security.services.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // Given
        String username = "test@test.com";
        User user = new User();
        user.setEmail(username);
        user.setPassword("password");
        user.setStatus(UserStatusEnum.ACTIVE);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        // Given
        String username = "nonexistent@test.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        // When / Then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        assertEquals("Invalid Credentials", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(username);
    }
}

