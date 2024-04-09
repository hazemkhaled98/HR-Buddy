package com.hrbuddy.rest.security;

import com.hrbuddy.rest.exceptions.ForbiddenException;
import com.hrbuddy.rest.exceptions.UnauthorizedException;
import jakarta.ws.rs.core.HttpHeaders;

public class SecurityManager {

    private SecurityManager() {
    }

    public static String generateToken(String role){
        return JwtUtil.generateToken(role);
    }

    public static void authorizeUser(HttpHeaders headers){
        extractAndValidateToken(headers);
    }

    public static void authorizeAdmin(HttpHeaders headers){
        String token = extractAndValidateToken(headers);
        
        if(!JwtUtil.isAdmin(token)){
            throw new ForbiddenException("Unauthorized Role for this operation");
        }
    }

    private static String extractAndValidateToken(HttpHeaders headers) {
        String token = headers.getHeaderString("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            throw new UnauthorizedException("No Token was provided");
        }

        token = token.substring(7);

        if(!JwtUtil.validateToken(token)){
            throw new UnauthorizedException("Invalid Token");
        }
        return token;
    }
}
