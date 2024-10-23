package com.ntd.challenge.operations.v1.controllers.public_apis;

import com.ntd.challenge.operations.v1.controllers.public_apis.documentation.OperationsControllerDocumentation;
import com.ntd.challenge.operations.v1.controllers.public_apis.requests.*;
import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResponse;
import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResult;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.services.OperationExecutorService;
import com.ntd.challenge.operations.v1.services.OperationService;
import com.ntd.challenge.operations.v1.services.RandomStringGeneratorService;
import com.ntd.challenge.operations.v1.services.impl.operation_commands.*;
import com.ntd.challenge.operations.v1.security.utils.AuthContextUtils;
import com.ntd.challenge.operations.v1.utils.MapperUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/operations")
public class OperationsController implements OperationsControllerDocumentation {

    private final OperationExecutorService operationExecutorService;
    private final OperationService operationService;
    private final RandomStringGeneratorService randomStringGeneratorService;

    @Override
    @GetMapping
    public List<OperationResponse> getAllOperations() {
        return operationService.getAll().stream().map(o -> MapperUtils.mapperInstance().map(o, OperationResponse.class)).toList();
    }

    @Override
    @GetMapping("add")
    public OperationResult<BigDecimal> add(@Valid AdditionRequest request) throws NotEnoughBalanceException {
        AddOperationCommand operationCommand = new AddOperationCommand(request.getValue1(), request.getValue2());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }

    @Override
    @GetMapping("subtract")
    public OperationResult<BigDecimal> subtract(@Valid SubtractRequest request) throws NotEnoughBalanceException {
        SubtractOperationCommand operationCommand = new SubtractOperationCommand(request.getValue1(), request.getValue2());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }

    @Override
    @GetMapping("multiply")
    public OperationResult<BigDecimal> multiply(@Valid MultiplyRequest request) throws NotEnoughBalanceException {
        MultiplyOperationCommand operationCommand = new MultiplyOperationCommand(request.getValue1(), request.getValue2());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }

    @Override
    @GetMapping("division")
    public OperationResult<BigDecimal> division(@Valid DivisionRequest request) throws NotEnoughBalanceException {
        DivisionOperationCommand operationCommand = new DivisionOperationCommand(request.getValue1(), request.getValue2(), request.getPrecision());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }

    @Override
    @GetMapping("square-root")
    public OperationResult<BigDecimal> squareRoot(@Valid SquareRootRequest request) throws NotEnoughBalanceException {
        SquareRootOperationCommand operationCommand = new SquareRootOperationCommand(request.getValue(), request.getPrecision());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }

    @Override
    @GetMapping("random-string")
    public OperationResult<String> randomString(@Valid RandomStringRequest request) throws NotEnoughBalanceException {
        RandomStringOperationCommand operationCommand = new RandomStringOperationCommand(randomStringGeneratorService, request.getSize());
        return new OperationResult<>(operationExecutorService.executeOperation(operationCommand, AuthContextUtils.getLoggedUserId()));
    }
}
