package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.InvalidOperationException;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.repositories.OperationsRepository;
import com.ntd.challenge.operations.v1.services.OperationCommand;
import com.ntd.challenge.operations.v1.services.OperationExecutorService;
import com.ntd.challenge.operations.v1.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationExecutorServiceImpl implements OperationExecutorService {

    private final OperationsRepository operationsRepository;
    private final RecordService recordService;

    @Override
    public <T> T executeOperation(OperationCommand<T> operationCommand, Integer userId) throws NotEnoughBalanceException {
        OperationTypeEnum operationType = operationCommand.getOperationType();

        Operation operation = operationsRepository.findByType(operationType)
                .orElseThrow(() -> new InvalidOperationException("Invalid operation"));

        T result = operationCommand.getOperationResult();

        recordService.addOperationToRecord(operation, userId, result.toString());

        return result;
    }
}
