package com.ntd.challenge.auth.v1.controllers.public_apis.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
}
