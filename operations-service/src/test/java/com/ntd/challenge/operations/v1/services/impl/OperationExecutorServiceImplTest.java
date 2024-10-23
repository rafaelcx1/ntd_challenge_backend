package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.InvalidOperationException;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.repositories.OperationsRepository;
import com.ntd.challenge.operations.v1.services.OperationCommand;
import com.ntd.challenge.operations.v1.services.RecordService;
import com.ntd.challenge.operations.v1.services.impl.operation_commands.AddOperationCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationExecutorServiceImplTest {

    @Mock
    private OperationsRepository operationsRepository;

    @Mock
    private RecordService recordService;

    @InjectMocks
    private OperationExecutorServiceImpl operationExecutorService;


    @Test
    void executeOperation_ShouldReturnResultAndRecordOperation() throws NotEnoughBalanceException {
        // Given
        Integer userId = 1;
        OperationCommand<BigDecimal> operationCommand = new AddOperationCommand(BigDecimal.ONE, BigDecimal.ONE);
        Operation operation = new Operation();
        operation.setType(OperationTypeEnum.ADDITION);

        when(operationsRepository.findByType(OperationTypeEnum.ADDITION)).thenReturn(Optional.of(operation));

        // When
        BigDecimal result = operationExecutorService.executeOperation(operationCommand, userId);

        // Then
        assertEquals(0, result.compareTo(new BigDecimal("2")));
        verify(recordService, times(1)).addOperationToRecord(eq(operation), eq(userId), eq("2"));
    }

    @Test
    void executeOperation_ShouldThrowInvalidOperationException_WhenOperationNotFound() throws NotEnoughBalanceException {
        // Given
        OperationCommand<BigDecimal> operationCommand = new AddOperationCommand(BigDecimal.ONE, BigDecimal.ONE);
        Integer userId = 1;

        when(operationsRepository.findByType(OperationTypeEnum.ADDITION)).thenReturn(Optional.empty());

        // Then
        assertThrows(InvalidOperationException.class, () ->
                operationExecutorService.executeOperation(operationCommand, userId)
        );

        verify(recordService, never()).addOperationToRecord(any(), any(), any());
    }

    @Test
    void executeOperation_ShouldThrowNotEnoughBalanceException_WhenBalanceIsInsufficient() throws NotEnoughBalanceException {
        // Given
        Integer userId = 1;
        OperationCommand<BigDecimal> operationCommand = new AddOperationCommand(BigDecimal.ONE, BigDecimal.ONE);
        Operation operation = new Operation();
        operation.setType(OperationTypeEnum.ADDITION);

        when(operationsRepository.findByType(OperationTypeEnum.ADDITION)).thenReturn(Optional.of(operation));
        doThrow(new NotEnoughBalanceException("Not enough balance")).when(recordService).addOperationToRecord(any(), any(), any());

        // Then
        assertThrows(NotEnoughBalanceException.class, () ->
                operationExecutorService.executeOperation(operationCommand, userId)
        );

        verify(recordService, times(1)).addOperationToRecord(any(), any(), any());
    }
}

