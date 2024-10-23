package com.ntd.challenge.operations.v1.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUser {


    private Integer id;
    private String email;


    public AuthenticatedUser(Integer userId, String username) {
        this.id = userId;
        this.email = username;
    }
}
