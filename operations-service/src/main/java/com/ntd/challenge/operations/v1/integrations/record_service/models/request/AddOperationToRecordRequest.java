package com.ntd.challenge.operations.v1.integrations.record_service.models.request;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AddOperationToRecordRequest {

    private Integer operationId;
    private Integer userId;
    private OperationTypeEnum type;
    private BigDecimal cost;
    private String result;
}
