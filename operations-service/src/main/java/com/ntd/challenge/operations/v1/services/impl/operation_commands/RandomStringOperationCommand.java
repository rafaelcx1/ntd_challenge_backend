package com.ntd.challenge.operations.v1.services.impl.operation_commands;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.services.OperationCommand;
import com.ntd.challenge.operations.v1.services.RandomStringGeneratorService;

public record RandomStringOperationCommand(RandomStringGeneratorService randomStringGeneratorService, Integer length) implements OperationCommand<String> {

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.RANDOM_STRING;
    }

    @Override
    public String getOperationResult() {
        return randomStringGeneratorService.generateRandomString(length);
    }
}
