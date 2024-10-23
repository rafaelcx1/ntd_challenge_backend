package com.ntd.challenge.operations.v1.controllers.public_apis.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RandomStringRequest {

    @NotNull(message = "The field 'size' is required")
    private Integer size;
}
