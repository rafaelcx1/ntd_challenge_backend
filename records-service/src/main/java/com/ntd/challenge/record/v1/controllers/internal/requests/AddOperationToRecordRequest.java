package com.ntd.challenge.record.v1.controllers.internal.requests;

import com.ntd.challenge.record.v1.entities.enums.OperationTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AddOperationToRecordRequest {

    @NotNull(message = "The field 'operationId' is required")
    private Integer operationId;

    @NotNull(message = "The field 'userId' is required")
    private Integer userId;

    @NotNull(message = "The field 'type' is required")
    private OperationTypeEnum type;

    @NotNull(message = "The field 'cost' is required")
    private BigDecimal cost;

    @NotEmpty(message = "The field 'result' is required")
    private String result;
}
