package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.DivideByZeroException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivisionOperationCommandTest {

    @Test
    void getOperationType_ShouldReturnDivision() {
        // Given
        BigDecimal value = new BigDecimal("10.5");
        BigDecimal divisor = new BigDecimal("5.5");
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, 2);

        // When
        OperationTypeEnum operationType = command.getOperationType();

        // Then
        assertEquals(OperationTypeEnum.DIVISION, operationType);
    }

    @Test
    void getOperationResult_ShouldReturnQuotientWithRounding() {
        // Given
        BigDecimal value = new BigDecimal("10.5");
        BigDecimal divisor = new BigDecimal("3");
        int scale = 2;
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, scale);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("3.50"), result);
    }

    @Test
    void getOperationResult_ShouldHandleNegativeValues() {
        // Given
        BigDecimal value = new BigDecimal("-10.5");
        BigDecimal divisor = new BigDecimal("2");
        int scale = 2;
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, scale);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("-5.25"), result);
    }

    @Test
    void getOperationResult_ShouldHandleZeroDivisor_ThrowArithmeticException() {
        // Given
        BigDecimal value = new BigDecimal("10.5");
        BigDecimal divisor = BigDecimal.ZERO;
        int scale = 2;
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, scale);

        // Then
        assertThrows(ArithmeticException.class, command::getOperationResult);
    }

    @Test
    void getOperationResult_ShouldThrowWhenValueIsZero() {
        // Given
        BigDecimal value = BigDecimal.ZERO;
        BigDecimal divisor = new BigDecimal("5.5");
        int scale = 2;
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, scale);

        assertThrows(DivideByZeroException.class,command::getOperationResult);
    }

    @Test
    void getOperationResult_ShouldHandleDecimalValuesWithRounding() {
        // Given
        BigDecimal value = new BigDecimal("10.123");
        BigDecimal divisor = new BigDecimal("3");
        int scale = 3;
        DivisionOperationCommand command = new DivisionOperationCommand(value, divisor, scale);

        // When
        BigDecimal result = command.getOperationResult();

        // Then
        assertEquals(new BigDecimal("3.374"), result);
    }
}

