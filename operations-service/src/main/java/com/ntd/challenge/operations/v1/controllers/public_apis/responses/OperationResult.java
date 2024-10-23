package com.ntd.challenge.operations.v1.controllers.public_apis.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResult<T> {

    private T result;
}
