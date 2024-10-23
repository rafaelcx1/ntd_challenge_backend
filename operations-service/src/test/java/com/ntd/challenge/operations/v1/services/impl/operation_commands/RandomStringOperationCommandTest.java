package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.services.RandomStringGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RandomStringOperationCommandTest {

    @Test
    void getOperationType_ShouldReturnRandomString() {
        // Given
        RandomStringGeneratorService randomStringGeneratorService = Mockito.mock(RandomStringGeneratorService.class);
        RandomStringOperationCommand command = new RandomStringOperationCommand(randomStringGeneratorService, 10);

        // When
        OperationTypeEnum operationType = command.getOperationType();

        // Then
        assertEquals(OperationTypeEnum.RANDOM_STRING, operationType);
    }

    @Test
    void getOperationResult_ShouldReturnGeneratedString() {
        // Given
        RandomStringGeneratorService randomStringGeneratorService = Mockito.mock(RandomStringGeneratorService.class);
        int length = 10;
        RandomStringOperationCommand command = new RandomStringOperationCommand(randomStringGeneratorService, length);

        when(randomStringGeneratorService.generateRandomString(anyInt())).thenReturn("RANDOMSTR");

        // When
        String result = command.getOperationResult();

        // Then
        assertEquals("RANDOMSTR", result);
        verify(randomStringGeneratorService, times(1)).generateRandomString(length);
    }

    @Test
    void getOperationResult_ShouldHandleDifferentLengths() {
        // Given
        RandomStringGeneratorService randomStringGeneratorService = Mockito.mock(RandomStringGeneratorService.class);
        int length = 5;
        RandomStringOperationCommand command = new RandomStringOperationCommand(randomStringGeneratorService, length);

        when(randomStringGeneratorService.generateRandomString(anyInt())).thenReturn("ABCDE");

        // When
        String result = command.getOperationResult();

        // Then
        assertEquals("ABCDE", result);
        verify(randomStringGeneratorService, times(1)).generateRandomString(length);
    }

    @Test
    void getOperationResult_ShouldReturnEmptyStringForZeroLength() {
        // Given
        RandomStringGeneratorService randomStringGeneratorService = Mockito.mock(RandomStringGeneratorService.class);
        int length = 0;
        RandomStringOperationCommand command = new RandomStringOperationCommand(randomStringGeneratorService, length);

        when(randomStringGeneratorService.generateRandomString(anyInt())).thenReturn("");

        // When
        String result = command.getOperationResult();

        // Then
        assertEquals("", result);
        verify(randomStringGeneratorService, times(1)).generateRandomString(length);
    }
}

