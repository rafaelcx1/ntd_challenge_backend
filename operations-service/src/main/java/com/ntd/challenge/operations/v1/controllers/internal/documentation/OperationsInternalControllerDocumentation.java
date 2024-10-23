package com.ntd.challenge.operations.v1.controllers.internal.documentation;

import com.ntd.challenge.operations.v1.controllers.public_apis.responses.OperationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Operations (Internal)")
public interface OperationsInternalControllerDocumentation {

    @Operation(summary = "Get all operation list")
    List<OperationResponse> getAllOperations();
}
