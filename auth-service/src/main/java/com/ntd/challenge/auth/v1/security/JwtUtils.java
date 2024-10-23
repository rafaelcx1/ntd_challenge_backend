package com.ntd.challenge.auth.v1.security;

import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.security.properties.JwtConfigurationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfigurationProperties properties;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public String generateAccessToken(User user) {
        String[] authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);
        claims.put("id", user.getId());

        return Jwts.builder()
                .subject(user.getUsername())
                .issuer(properties.getIssuer())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + properties.getExpiration()))
                .claims(claims)
                .signWith(getSigningKey())
                .compact();
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

    public UserDetails getUserFromToken(String token) {
        Claims claims = parseClaims(token);
        String username = claims.getSubject();
        int userId = (Integer) claims.get("id");

        return new User(userId, username);
    }
}
