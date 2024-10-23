package com.ntd.challenge.auth.v1.security.filters;

import com.ntd.challenge.auth.v1.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
 
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
 
        String token = getAccessToken(request);
 
        if (!jwtUtils.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
 
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }
 
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
    }
 
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }
 
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = jwtUtils.getUserFromToken(token);
 
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
 
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}