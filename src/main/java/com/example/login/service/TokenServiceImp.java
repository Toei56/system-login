package com.example.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login.entityModel.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenServiceImp implements TokenService {

    @Value("${spring.token.secret}")
    private String secret;
    @Value("${spring.token.issuer}")
    private String issuer;

    @Override
    public String tokenize(User user) {
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(30));

        return JWT
                .create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", user.getRole())
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    @Override
    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm())
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token);
        } catch (Exception ex) {
            return null;
        }
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
}
