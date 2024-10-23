package com.ntd.challenge.operations.v1.security.utils;

import com.ntd.challenge.operations.v1.security.model.AuthenticatedUser;
import com.ntd.challenge.operations.v1.exceptions.types.NotLoggedUserException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class AuthContextUtils {

    public static AuthenticatedUser getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }

    public static Integer getLoggedUserId() {
        AuthenticatedUser loggedUser = getLoggedUser();

        if (loggedUser == null) {
            throw new NotLoggedUserException();
        }

        return loggedUser.getId();
    }
}