package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.DivideByZeroException;
import com.ntd.challenge.operations.v1.exceptions.types.SquareRootNegativeNumberException;
import com.ntd.challenge.operations.v1.services.OperationCommand;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record DivisionOperationCommand(BigDecimal value, BigDecimal divisor, Integer scale) implements OperationCommand<BigDecimal> {

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.DIVISION;
    }

    @Override
    public BigDecimal getOperationResult() {
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new DivideByZeroException();
        }

        return value.divide(divisor, scale, RoundingMode.DOWN);
    }
}
