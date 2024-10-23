package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.entities.Operation;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.integrations.record_service.RecordServiceIntegration;
import com.ntd.challenge.operations.v1.integrations.record_service.exceptions.RecordServiceForbiddenException;
import com.ntd.challenge.operations.v1.integrations.record_service.models.request.AddOperationToRecordRequest;
import com.ntd.challenge.operations.v1.integrations.record_service.models.response.enums.BadRequestErrorTypeEnum;
import com.ntd.challenge.operations.v1.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordServiceIntegration recordServiceIntegration;


    @Override
    public void addOperationToRecord(Operation operation, Integer userId, String commandResult) throws NotEnoughBalanceException {
        AddOperationToRecordRequest request = AddOperationToRecordRequest.builder()
                .operationId(operation.getId())
                .type(operation.getType())
                .cost(operation.getCost())
                .result(commandResult)
                .userId(userId)
                .build();

        try {
            recordServiceIntegration.addOperationToRecord(request);
        } catch (RecordServiceForbiddenException e) {
            if (e.getResponse().getTitle().equals(BadRequestErrorTypeEnum.NOT_ENOUGH_BALANCE.name())) {
                throw new NotEnoughBalanceException("Not enough balance to complete this operation");
            }
        }
    }
}
