package com.ntd.challenge.operations.v1.controllers.public_apis.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SquareRootRequest {

    @NotNull(message = "The field 'value' is required")
    private BigDecimal value;

    private Integer precision = 6;
}
