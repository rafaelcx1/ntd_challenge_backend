package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.repositories.OperationsRepository;
import com.ntd.challenge.operations.v1.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationsRepository operationsRepository;

    @Override
    public List<Operation> getAll() {
        return operationsRepository.findAll();
    }
}
