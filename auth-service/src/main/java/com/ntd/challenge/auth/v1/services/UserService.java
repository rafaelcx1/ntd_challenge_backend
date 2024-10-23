package com.ntd.challenge.auth.v1.services;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.entities.User;

import java.util.Optional;

public interface UserService {


    Optional<User> findByEmail(String username);

    void registerUser(UserCreateRequest request);

    User get();
}
