package com.ntd.challenge.record.v1.controllers.public_apis.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class RecordResponse {

    private Integer id;
    private Integer operationId;
    private Integer userId;
    private BigDecimal amount;
    private BigDecimal userBalance;
    private String operationResponse;
    private Instant date;
}
