package com.example.securitytest.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.securitytest.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class JwtTokenService {

    private String issuer = "auth-api";

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        return this.generateToken(user, null);
    }

    public String generateToken(User user, Map payload) {
        try {
            var builder = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withExpiresAt(this.genExpirationDate());

            if (payload != null) builder.withPayload(Map.of("payload", payload));

            String token = builder.sign(this.getAlgorithm());
            return token;
        } catch (JWTCreationException jwtCreationException) {
            throw new RuntimeException("Error while generating token", jwtCreationException);
        }
    }

    public String validateToken(String token) {
        try {
            return getDecodedToken(token)
                    .getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            return "";
        }
    }

    public Map<String, Object> getPayload(String token) {
        return getDecodedToken(token).getClaims().get("payload").asMap();
    }

    public DecodedJWT getDecodedToken(String token) {
        return JWT.require(this.getAlgorithm())
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        return algorithm;
    }
}
