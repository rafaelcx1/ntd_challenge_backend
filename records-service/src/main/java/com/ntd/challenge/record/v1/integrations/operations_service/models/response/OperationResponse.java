package com.ntd.challenge.record.v1.integrations.operations_service.models.response;

import com.ntd.challenge.record.v1.entities.enums.OperationTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class OperationResponse {
    private Integer id;
    private Instant creationDate;
    private OperationTypeEnum type;
    private BigDecimal cost;
}
