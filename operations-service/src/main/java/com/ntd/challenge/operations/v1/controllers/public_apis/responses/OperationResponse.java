package com.ntd.challenge.operations.v1.controllers.public_apis.responses;

import com.ntd.challenge.operations.v1.entities.enums.OperationTypeEnum;
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
