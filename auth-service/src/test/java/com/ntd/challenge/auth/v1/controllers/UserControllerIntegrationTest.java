package com.ntd.challenge.auth.v1.controllers;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.entities.enums.UserStatusEnum;
import com.ntd.challenge.auth.v1.repositories.UserRepository;
import com.ntd.challenge.auth.v1.test.utils.AbstractIntegrationTest;
import com.ntd.challenge.auth.v1.utils.AuthContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "loggeduser@test.com")
    void getUser_ShouldReturnLoggedUser_WhenUserIsLoggedIn() throws Exception {
        // Given
        User loggedInUser = new User();
        loggedInUser.setEmail("loggeduser@test.com");
        loggedInUser.setPassword("password");
        userRepository.save(loggedInUser);

        try(MockedStatic<AuthContextUtils> mockedAuthContext = mockStatic(AuthContextUtils.class)) {
            mockedAuthContext.when(AuthContextUtils::getLoggedUser).thenReturn(loggedInUser);

            // When & Then
            mockMvc.perform(get("/v1/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("loggeduser@test.com"));
        }
    }

    @Test
    void getUser_ShouldReturnAccessDenied_WhenUserIsNotLoggedIn() throws Exception {
        try(MockedStatic<AuthContextUtils> mockedAuthContext = mockStatic(AuthContextUtils.class)) {
            mockedAuthContext.when(AuthContextUtils::getLoggedUser).thenReturn(null);

            // When & Then
            mockMvc.perform(get("/v1/users"))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void registerUser_ShouldRegisterNewUser_WhenRequestIsValid() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail("newuser@test.com");
        userCreateRequest.setPassword("password123");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // When & Then
        mockMvc.perform(post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isOk());

        Optional<User> registeredUser = userRepository.findByEmail("newuser@test.com");
        assertTrue(registeredUser.isPresent());
    }

    @Test
    void registerUser_ShouldThrowExistentUserException_WhenUserAlreadyExists() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail("existinguser@test.com");
        userCreateRequest.setPassword("password123");

        User existentUser = new User();
        existentUser.setStatus(UserStatusEnum.ACTIVE);
        existentUser.setEmail("existinguser@test.com");
        existentUser.setPassword("password123");
        userRepository.save(existentUser);

        // When & Then
        mockMvc.perform(post("/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isBadRequest());
    }
}
