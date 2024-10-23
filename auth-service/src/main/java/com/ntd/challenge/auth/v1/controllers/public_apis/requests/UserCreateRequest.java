package com.ntd.challenge.auth.v1.controllers.public_apis.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @Email(message = "Invalid email")
    @NotEmpty(message = "The field 'email' is required")
    private String email;

    @NotEmpty(message = "The field 'password' is required")
    private String password;
}
