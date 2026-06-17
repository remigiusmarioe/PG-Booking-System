package com.pgbooking.user.service;

import com.pgbooking.user.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import  java.nio.charset.StandardCharsets;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySecretKeymySecretKeymySecretKeymySecretKey";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Generate Jwt Token using user's email
    public String generateToken(String email, Role role) {

        return Jwts.builder()
                //user dentity
                .subject(email)
                .claim("role",role.name())
                //token creation time
                .issuedAt(new Date())
                //token valid for 24 hrs
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                //sign token with secret key
                .signWith(getSecretKey())
                //build token
                .compact();

    }

    public  boolean validateToken(String token , String email) {

        String extractedEmail = extractEmail(token);

        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    public  boolean isTokenExpired(String token ) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractRole(String token) {
        return extractClaims(token)
                .get("role",String.class);
    }

}
