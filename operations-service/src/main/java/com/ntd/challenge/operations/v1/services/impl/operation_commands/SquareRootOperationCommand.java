package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.SquareRootNegativeNumberException;
import com.ntd.challenge.operations.v1.services.OperationCommand;

import java.math.BigDecimal;
import java.math.MathContext;

public record SquareRootOperationCommand(BigDecimal value, Integer precision) implements OperationCommand<BigDecimal> {

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.SQUARE_ROOT;
    }

    @Override
    public BigDecimal getOperationResult() {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new SquareRootNegativeNumberException();
        }

        return value.sqrt(new MathContext(precision));
    }
}
