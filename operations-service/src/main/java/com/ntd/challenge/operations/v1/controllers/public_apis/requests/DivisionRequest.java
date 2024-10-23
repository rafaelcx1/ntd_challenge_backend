package com.ntd.challenge.operations.v1.controllers.public_apis.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DivisionRequest {

    @NotNull(message = "The field 'value1' is required")
    private BigDecimal value1;

    @NotNull(message = "The field 'value2' is required")
    private BigDecimal value2;

    private Integer precision = 6;
}
