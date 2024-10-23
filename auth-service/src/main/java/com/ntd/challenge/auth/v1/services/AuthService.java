package com.ntd.challenge.auth.v1.services;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);
}
