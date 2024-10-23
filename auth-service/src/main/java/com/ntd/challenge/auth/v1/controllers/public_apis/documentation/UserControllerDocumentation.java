package com.ntd.challenge.auth.v1.controllers.public_apis.documentation;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.controllers.public_apis.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users")
public interface UserControllerDocumentation {

    @Operation(summary = "Get logged user")
    @SecurityRequirement(name = "NTD_Software")
    UserResponse get();

    @Operation(summary = "Register user")
    void register(UserCreateRequest request);
}
