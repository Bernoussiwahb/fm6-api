package com.fm6.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // Clé secrète (256 bits) – à garder privée !
    private final Key secretKey = Keys.hmacShaKeyFor(
        "bF!GkR9hVnZp3s6v9y$B&E)H@McQfTjW".getBytes());

    private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 h

    /** Génère un token pour l'utilisateur (sujet = username) */
    public String generateToken(String username) {
        return Jwts.builder()
                   .setClaims(Map.of())        // pas de claims custom pour l'instant
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_MS))
                   .signWith(secretKey, SignatureAlgorithm.HS256)
                   .compact();
    }

    /** Extrait le username contenu dans le token */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** Renvoie true si token non expiré et signature correcte */
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /* --------------- Helpers privés --------------- */

    private <T> T extractClaim(String token, Function<Claims,T> resolver) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return resolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }
}
