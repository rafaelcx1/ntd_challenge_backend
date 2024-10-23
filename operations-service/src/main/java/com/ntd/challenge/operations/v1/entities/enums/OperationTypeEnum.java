package com.ntd.challenge.operations.v1.entities.enums;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public enum OperationTypeEnum {
    ADDITION(BigDecimal.class),
    SUBTRACTION(BigDecimal.class),
    MULTIPLICATION(BigDecimal.class),
    DIVISION(BigDecimal.class),
    SQUARE_ROOT(BigDecimal.class),
    RANDOM_STRING(String.class);

    private final Class returnType;
}
