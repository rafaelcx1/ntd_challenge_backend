package com.ntd.challenge.auth.v1.controllers.public_apis.documentation;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;
import com.ntd.challenge.auth.v1.controllers.public_apis.responses.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth")
public interface AuthControllerDocumentation {

    @Operation(summary = "Authenticate user")
    LoginResponse login(LoginRequest loginRequest);
}
