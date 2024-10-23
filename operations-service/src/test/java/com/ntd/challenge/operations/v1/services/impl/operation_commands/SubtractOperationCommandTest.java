package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SubtractOperationCommandTest {

    @Test
    void getOperationType_ShouldReturnSubtraction() {
        // Given
        BigDecimal value1 = new BigDecimal("10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        SubtractOperationCommand command = new SubtractOperationCommand(value1, value2);

        // When
        OperationTypeEnum operationType = command.getOperationType();

        // Then
        assertEquals(OperationTypeEnum.SUBTRACTION, operationType);
    }

    @Test
    void getOperationResult_ShouldReturnDifferenceOfValues() {
        // Given
        BigDecimal value1 = new BigDecimal("10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        SubtractOperationCommand command = new SubtractOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("5.0"), result);
    }

    @Test
    void getOperationResult_ShouldHandleNegativeValues() {
        // Given
        BigDecimal value1 = new BigDecimal("-10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        SubtractOperationCommand command = new SubtractOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("-16.0"), result);
    }

    @Test
    void getOperationResult_ShouldHandleZeroValues() {
        // Given
        BigDecimal value1 = BigDecimal.ZERO;
        BigDecimal value2 = BigDecimal.ZERO;
        SubtractOperationCommand command = new SubtractOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void getOperationResult_ShouldHandleDecimalValues() {
        // Given
        BigDecimal value1 = new BigDecimal("10.123");
        BigDecimal value2 = new BigDecimal("5.456");
        SubtractOperationCommand command = new SubtractOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("4.667"), result);
    }
}

