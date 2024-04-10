package com.hrbuddy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {

    private static String secretKey;

    private JwtUtil() {}

    static {
        loadSecretKey();
    }

    private static void loadSecretKey() {
        Properties prop = new Properties();
        try (InputStream input = JwtUtil.class.getClassLoader().getResourceAsStream("jwt.properties")) {
            prop.load(input);
            secretKey = prop.getProperty("jwt.secret");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String generateToken(String role) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(role)
                .issuer("hrbuddy")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60 * 24)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAdmin(String token) {
        return validateToken(token) && "admin".equals(Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }


}
