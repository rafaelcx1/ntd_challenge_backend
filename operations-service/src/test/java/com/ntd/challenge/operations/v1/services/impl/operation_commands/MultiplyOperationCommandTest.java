package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplyOperationCommandTest {

    @Test
    void getOperationType_ShouldReturnMultiplication() {
        // Given
        BigDecimal value1 = new BigDecimal("10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        MultiplyOperationCommand command = new MultiplyOperationCommand(value1, value2);

        // When
        OperationTypeEnum operationType = command.getOperationType();

        // Then
        assertEquals(OperationTypeEnum.MULTIPLICATION, operationType);
    }

    @Test
    void getOperationResult_ShouldReturnProductOfValues() {
        // Given
        BigDecimal value1 = new BigDecimal("10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        MultiplyOperationCommand command = new MultiplyOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("57.75"), result);
    }

    @Test
    void getOperationResult_ShouldHandleNegativeValues() {
        // Given
        BigDecimal value1 = new BigDecimal("-10.5");
        BigDecimal value2 = new BigDecimal("5.5");
        MultiplyOperationCommand command = new MultiplyOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("-57.75"), result);
    }

    @Test
    void getOperationResult_ShouldHandleZeroValues() {
        // Given
        BigDecimal value1 = BigDecimal.ZERO;
        BigDecimal value2 = new BigDecimal("5.5");
        MultiplyOperationCommand command = new MultiplyOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(BigDecimal.ZERO));
    }

    @Test
    void getOperationResult_ShouldHandleDecimalValues() {
        // Given
        BigDecimal value1 = new BigDecimal("10.123");
        BigDecimal value2 = new BigDecimal("5.456");
        MultiplyOperationCommand command = new MultiplyOperationCommand(value1, value2);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(new BigDecimal("55.231088")));
    }
}

