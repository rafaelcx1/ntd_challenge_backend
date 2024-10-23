package com.ntd.challenge.record.v1.services.impl;

import com.ntd.challenge.record.v1.configurations.WalletProperties;
import com.ntd.challenge.record.v1.controllers.internal.requests.AddOperationToRecordRequest;
import com.ntd.challenge.record.v1.entities.Record;
import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
import com.ntd.challenge.record.v1.integrations.operations_service.OperationsServiceIntegration;
import com.ntd.challenge.record.v1.integrations.operations_service.models.response.OperationResponse;
import com.ntd.challenge.record.v1.repositories.RecordRepository;
import com.ntd.challenge.record.v1.security.utils.AuthContextUtils;
import com.ntd.challenge.record.v1.services.RecordService;
import com.ntd.challenge.record.v1.utils.TimezoneUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.ntd.challenge.record.v1.repositories.specifications.RecordSpecifications.*;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final WalletProperties walletProperties;
    private final RecordRepository recordRepository;
    private final OperationsServiceIntegration operationsServiceIntegration;


    @Override
    public synchronized void addOperationToRecord(AddOperationToRecordRequest request) throws NotEnoughBalanceException {
        BigDecimal userBalance = getUserBalance(request.getUserId());

        if (userBalance.compareTo(request.getCost()) < 0) {
            throw new NotEnoughBalanceException("Not enough balance to complete this operation.");
        }

        BigDecimal newBalance = userBalance.subtract(request.getCost());

        Record record = Record.builder()
                .operationId(request.getOperationId())
                .date(Instant.now())
                .amount(request.getCost())
                .userBalance(newBalance)
                .operationResponse(request.getResult())
                .userId(request.getUserId())
                .build();

        recordRepository.save(record);
    }

    @Override
    public BigDecimal getLoggedUserBalance() {
        return getUserBalance(AuthContextUtils.getLoggedUserId());
    }

    @Override
    public Page<Record> getRecords(String filter, Pageable pageable) {
        List<Integer> operationsIdLike = operationsServiceIntegration.getAllOperations().stream()
                .filter(o -> o.getType().name().contains(filter))
                .map(OperationResponse::getId)
                .toList();

        Specification<Record> spec = notDeletedRecord()
                .and(userId(AuthContextUtils.getLoggedUserId()))
                .and(operationsIdIn(operationsIdLike)
                        .or(amountLike(filter))
                        .or(userBalanceLike(filter))
                        .or(operationResponseLike(filter))
                        .or(dateLike(filter, TimezoneUtils.getTimezone()))
                );

        return recordRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public void deleteRecord(Integer id) throws RecordNotFoundException {
        Integer loggedUserId = AuthContextUtils.getLoggedUserId();

        Record record = recordRepository.findByIdAndUserIdAndIsDeletedFalse(id, loggedUserId)
                .orElseThrow(() -> new RecordNotFoundException("Record not found"));

        recordRepository.updateUserBalanceInRecordsAfter(loggedUserId, record.getId(), record.getAmount());

        record.setDeleted(true);
        recordRepository.save(record);
    }

    private BigDecimal getUserBalance(Integer userId) {
        return recordRepository.findUserBalanceFromLastRecord(userId)
                .orElse(walletProperties.getInitialBalance());
    }
}
