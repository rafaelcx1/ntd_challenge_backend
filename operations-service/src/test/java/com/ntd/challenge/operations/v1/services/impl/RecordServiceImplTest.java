package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.integrations.record_service.RecordServiceIntegration;
import com.ntd.challenge.operations.v1.integrations.record_service.exceptions.RecordServiceForbiddenException;
import com.ntd.challenge.operations.v1.integrations.record_service.models.request.AddOperationToRecordRequest;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.Record4XXResponse;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.enums.BadRequestErrorTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceImplTest {

    @Mock
    private RecordServiceIntegration recordServiceIntegration;

    @InjectMocks
    private RecordServiceImpl recordService;

    @Test
    void addOperationToRecord_ShouldCallIntegrationWithCorrectRequest() throws NotEnoughBalanceException {
        // Given
        Operation operation = mock(Operation.class);
        when(operation.getId()).thenReturn(1);
        when(operation.getType()).thenReturn(OperationTypeEnum.ADDITION);
        when(operation.getCost()).thenReturn(new BigDecimal("10.00"));
        Integer userId = 100;
        String commandResult = "RESULT";

        // When
        recordService.addOperationToRecord(operation, userId, commandResult);

        // Then
        verify(recordServiceIntegration, times(1)).addOperationToRecord(any(AddOperationToRecordRequest.class));
    }

    @Test
    void addOperationToRecord_ShouldThrowNotEnoughBalanceException() {
        // Given
        Operation operation = mock(Operation.class);
        when(operation.getId()).thenReturn(1);
        when(operation.getType()).thenReturn(OperationTypeEnum.ADDITION);
        when(operation.getCost()).thenReturn(new BigDecimal("10.00"));
        Integer userId = 100;
        String commandResult = "RESULT";

        RecordServiceForbiddenException forbiddenException = mock(RecordServiceForbiddenException.class);
        Record4XXResponse errorResponse = new Record4XXResponse();
        errorResponse.setTitle(BadRequestErrorTypeEnum.NOT_ENOUGH_BALANCE.name());
        when(forbiddenException.getResponse()).thenReturn(errorResponse);

        doThrow(forbiddenException).when(recordServiceIntegration).addOperationToRecord(any(AddOperationToRecordRequest.class));

        // Then
        assertThrows(NotEnoughBalanceException.class, () -> {
            recordService.addOperationToRecord(operation, userId, commandResult);
        });

        verify(recordServiceIntegration, times(1)).addOperationToRecord(any(AddOperationToRecordRequest.class));
    }
}

