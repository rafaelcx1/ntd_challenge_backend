package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareRootOperationCommandTest {

    @Test
    void getOperationType_ShouldReturnSquareRoot() {
        // Given
        BigDecimal value = new BigDecimal("16");
        int precision = 2;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // When
        OperationTypeEnum operationType = command.getOperationType();

        // Then
        assertEquals(OperationTypeEnum.SQUARE_ROOT, operationType);
    }

    @Test
    void getOperationResult_ShouldReturnSquareRootWithGivenPrecision() {
        // Given
        BigDecimal value = new BigDecimal("16");
        int precision = 2;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(new BigDecimal("4.0")));
    }

    @Test
    void getOperationResult_ShouldReturnSquareRootForDecimalValue() {
        // Given
        BigDecimal value = new BigDecimal("2");
        int precision = 3;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(new BigDecimal("1.41")));
    }

    @Test
    void getOperationResult_ShouldHandleHighPrecision() {
        // Given
        BigDecimal value = new BigDecimal("10");
        int precision = 10;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(new BigDecimal("3.162277660")));
    }

    @Test
    void getOperationResult_ShouldThrowExceptionForNegativeValue() {
        // Given
        BigDecimal value = new BigDecimal("-16");
        int precision = 2;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // Then
        assertThrows(ArithmeticException.class, command::getOperationResult);
    }

    @Test
    void getOperationResult_ShouldHandleZeroValue() {
        // Given
        BigDecimal value = BigDecimal.ZERO;
        int precision = 2;
        SquareRootOperationCommand command = new SquareRootOperationCommand(value, precision);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(0, result.compareTo(BigDecimal.ZERO));
    }
}

