package com.eventdriven.authservice.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import java.util.Date;
import org.springframework.security.core.userdetails.User;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {


    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String ISSUER = "ISSUER";



    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 300_000))// 5 min
                .signWith(SECRET_KEY)
                .compact();
        
    }

    // Refresh token üretmek için
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 604_800_000)) // 7 gün
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static Boolean isTokenValid(String token) {
        return !isExpired(token);
    }

    private static boolean isExpired(String token) {

        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

}
