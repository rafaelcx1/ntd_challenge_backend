package com.ntd.challenge.operations.v1.services;

import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;

public interface OperationExecutorService {

    <T> T executeOperation(OperationCommand<T> operationCommand, Integer userId) throws NotEnoughBalanceException;
}
