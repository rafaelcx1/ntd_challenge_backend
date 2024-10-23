package com.ntd.challenge.operations.v1.services;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;

public interface OperationCommand<T> {
    OperationTypeEnum getOperationType();
    T getOperationResult();
}
