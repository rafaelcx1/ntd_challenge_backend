package com.ntd.challenge.operations.v1.controllers.public_apis.documentation;

import com.ntd.challenge.operations.v1.controllers.public_apis.requests.*;
import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResponse;
import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResult;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Operations")
public interface OperationsControllerDocumentation {

    @Operation(summary = "Get all operation list")
    List<OperationResponse> getAllOperations();

    @Operation(summary = "Make an addition operation")
    OperationResult<BigDecimal> add(AdditionRequest request) throws NotEnoughBalanceException;

    @Operation(summary = "Make a subtraction operation")
    OperationResult<BigDecimal> subtract(SubtractRequest request) throws NotEnoughBalanceException;

    @Operation(summary = "Make a multiply operation")
    OperationResult<BigDecimal> multiply(MultiplyRequest request) throws NotEnoughBalanceException;

    @Operation(summary = "Make a division operation")
    OperationResult<BigDecimal> division(DivisionRequest request) throws NotEnoughBalanceException;

    @Operation(summary = "Make a square root operation")
    OperationResult<BigDecimal> squareRoot(SquareRootRequest request) throws NotEnoughBalanceException;

    @Operation(summary = "Make a random string operation")
    OperationResult<String> randomString(RandomStringRequest request) throws NotEnoughBalanceException;
}
