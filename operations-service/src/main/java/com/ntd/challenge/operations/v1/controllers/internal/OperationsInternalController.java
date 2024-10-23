package com.ntd.challenge.operations.v1.controllers.internal;

import com.ntd.challenge.operations.v1.controllers.internal.documentation.OperationsInternalControllerDocumentation;
import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResponse;
import com.ntd.challenge.operations.v1.services.OperationService;
import com.ntd.challenge.operations.v1.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/v1/operations")
public class OperationsInternalController implements OperationsInternalControllerDocumentation {

    private final OperationService operationService;

    @Override
    @GetMapping
    public List<OperationResponse> getAllOperations() {
        return operationService.getAll().stream().map(o -> MapperUtils.mapperInstance().map(o, OperationResponse.class)).toList();
    }
}
