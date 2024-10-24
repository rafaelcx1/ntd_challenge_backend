package com.ntd.challenge.record.v1.services.impl;

import com.ntd.challenge.record.v1.configurations.WalletProperties;
import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
import com.ntd.challenge.record.v1.integrations.operations_service.OperationsServiceIntegration;
import com.ntd.challenge.record.v1.repositories.RecordRepository;
import com.ntd.challenge.record.v1.security.utils.AuthContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ntd.challenge.record.v1.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceImplTest {

    @Mock
    private WalletProperties walletProperties;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private OperationsServiceIntegration operationsServiceIntegration;

    @InjectMocks
    private RecordServiceImpl recordService;

    @Test
    void addOperationToRecord_ShouldAddRecordWhenBalanceIsSufficient() throws NotEnoughBalanceException {
        // Given
        Integer userId = 1;
        AddOperationToRecordRequest request = new AddOperationToRecordRequest();
        request.setUserId(userId);
        request.setCost(new BigDecimal("50.00"));
        request.setOperationId(1);
        request.setResult("1");

        when(recordRepository.findUserBalanceFromLastRecord(userId))
                .thenReturn(Optional.of(new BigDecimal("100.00")));

        // When
        recordService.addOperationToRecord(request);

        // Then
        ArgumentCaptor<Record> recordCaptor = ArgumentCaptor.forClass(Record.class);
        verify(recordRepository).save(recordCaptor.capture());
        Record savedRecord = recordCaptor.getValue();

        assertEquals(0, savedRecord.getAmount().compareTo(new BigDecimal("50.00")));
        assertEquals(0, savedRecord.getUserBalance().compareTo(new BigDecimal("50.00")));
        assertEquals("1", savedRecord.getOperationResponse());
    }

    @Test
    void addOperationToRecord_ShouldThrowExceptionWhenBalanceIsInsufficient() {
        // Given
        Integer userId = 1;
        AddOperationToRecordRequest request = new AddOperationToRecordRequest();
        request.setUserId(userId);
        request.setCost(new BigDecimal("150.00"));

        when(recordRepository.findUserBalanceFromLastRecord(userId))
                .thenReturn(Optional.of(new BigDecimal("100.00")));

        // When & Then
        assertThrows(NotEnoughBalanceException.class, () -> {
            recordService.addOperationToRecord(request);
        });

        verify(recordRepository, never()).save(any(Record.class));
    }

    @Test
    void getLoggedUserBalance_ShouldReturnBalance() {
        // Given
        Integer loggedUserId = 1;
        BigDecimal expectedBalance = new BigDecimal("200.00");

        try (MockedStatic<AuthContextUtils> authContextMock = mockStatic(AuthContextUtils.class)) {
            authContextMock.when(AuthContextUtils::getLoggedUserId).thenReturn(loggedUserId);
            when(recordRepository.findUserBalanceFromLastRecord(loggedUserId))
                    .thenReturn(Optional.of(expectedBalance));

            // When
            BigDecimal userBalance = recordService.getLoggedUserBalance();

            // Then
            assertEquals(expectedBalance, userBalance);
        }
    }

    @Test
    void getRecords_ShouldReturnPagedRecords() {
        // Given
        Integer loggedUserId = 1;
        String filter = "";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Record> expectedPage = new PageImpl<>(List.of(new Record()));

        when(recordRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(expectedPage);

        try (MockedStatic<AuthContextUtils> authContextMock = mockStatic(AuthContextUtils.class)) {
            authContextMock.when(AuthContextUtils::getLoggedUserId).thenReturn(loggedUserId);
            // When
            Page<Record> result = recordService.getRecords(filter, pageable);

            // Then
            assertEquals(expectedPage, result);
        }
    }

    @Test
    void deleteRecord_ShouldMarkRecordAsDeleted() throws RecordNotFoundException {
        // Given
        Integer loggedUserId = 1;
        Integer recordId = 123;
        Instant recordDate = Instant.now();
        BigDecimal recordAmount = new BigDecimal("50.00");

        Record record = Record.builder()
                .id(recordId)
                .date(recordDate)
                .amount(recordAmount)
                .build();

        try (MockedStatic<AuthContextUtils> authContextMock = mockStatic(AuthContextUtils.class)) {
            authContextMock.when(AuthContextUtils::getLoggedUserId).thenReturn(loggedUserId);

            when(recordRepository.findByIdAndUserIdAndIsDeletedFalse(recordId, loggedUserId))
                    .thenReturn(Optional.of(record));

            // When
            recordService.deleteRecord(recordId);

            // Then
            assertTrue(record.isDeleted());
            verify(recordRepository).updateUserBalanceInRecordsAfter(loggedUserId, recordId, recordAmount);
            verify(recordRepository).save(record);
        }
    }

    @Test
    void deleteRecord_ShouldThrowExceptionWhenRecordNotFound() {
        // Given
        Integer loggedUserId = 1;
        Integer recordId = 123;

        try (MockedStatic<AuthContextUtils> authContextMock = mockStatic(AuthContextUtils.class)) {
            authContextMock.when(AuthContextUtils::getLoggedUserId).thenReturn(loggedUserId);

            when(recordRepository.findByIdAndUserIdAndIsDeletedFalse(recordId, loggedUserId)).thenReturn(Optional.empty());

            // When & Then
            assertThrows(RecordNotFoundException.class, () -> {
                recordService.deleteRecord(recordId);
            });

            verify(recordRepository, never()).updateUserBalanceInRecordsAfter(anyInt(), any(), any());
            verify(recordRepository, never()).save(any(Record.class));
        }
    }
}

