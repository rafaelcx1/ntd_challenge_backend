package com.ntd.challenge.auth.v1.utils;

import com.ntd.challenge.auth.v1.entities.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class AuthContextUtils {

    public static User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }
}
