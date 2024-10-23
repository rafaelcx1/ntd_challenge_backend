package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.integrations.random_org.RandomOrgIntegration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RandomStringGeneratorServiceImplTest {

    @Mock
    private RandomOrgIntegration randomStringIntegration;

    @InjectMocks
    private RandomStringGeneratorServiceImpl randomStringGeneratorService;

    @Test
    void generateRandomString_ShouldReturnRandomStringFromIntegration() {
        // Given
        int length = 10;
        when(randomStringIntegration.getRandomString(anyInt())).thenReturn("RANDOMSTRR");

        // When
        String result = randomStringGeneratorService.generateRandomString(length);

        // Then
        assertEquals("RANDOMSTRR", result);
        verify(randomStringIntegration, times(1)).getRandomString(length);
    }

    @Test
    void generateRandomString_ShouldHandleDifferentLengths() {
        // Given
        int length = 5;
        when(randomStringIntegration.getRandomString(anyInt())).thenReturn("ABCDE");

        // When
        String result = randomStringGeneratorService.generateRandomString(length);

        // Then
        assertEquals("ABCDE", result);
        verify(randomStringIntegration, times(1)).getRandomString(length);
    }
}

