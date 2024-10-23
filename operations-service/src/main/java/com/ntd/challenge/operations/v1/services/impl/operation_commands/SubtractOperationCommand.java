package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.services.OperationCommand;

import java.math.BigDecimal;

public record SubtractOperationCommand(BigDecimal value1, BigDecimal value2) implements OperationCommand<BigDecimal> {

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.SUBTRACTION;
    }

    @Override
    public BigDecimal getOperationResult() {
        return value1.subtract(value2);
    }
}
