package com.ntd.challenge.operations.v1.security.utils;

import com.ntd.challenge.operations.v1.security.model.AuthenticatedUser;
import com.ntd.challenge.operations.v1.security.properties.JwtConfigurationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfigurationProperties properties;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public boolean validateAccessToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public AuthenticatedUser getUserFromToken(String token) {
        Claims claims = parseClaims(token);
        String username = claims.getSubject();
        Integer userId = (Integer) claims.get("id");

        return new AuthenticatedUser(userId, username);
    }
}
