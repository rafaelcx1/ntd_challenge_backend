package com.ntd.challenge.auth.v1.controllers.public_apis.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty(message = "The field 'email' is required")
    private String email;

    @NotEmpty(message = "The field 'password' is required")
    private String password;
}
