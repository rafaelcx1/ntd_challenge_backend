package com.ntd.challenge.auth.v1.services.impl;

import com.ntd.challenge.auth.v1.security.JwtUtils;
import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;


    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        var user = (User) authentication.getPrincipal();

        return jwtUtils.generateAccessToken(user);
    }
}
