package com.nexio.auth.service;

import com.nexio.auth.entity.UserAccount;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private static final long ACCESS_TOKEN_SECONDS =
            15 * 60;

    private final JwtEncoder jwtEncoder;

    public JwtService(
            JwtEncoder jwtEncoder
    ) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateAccessToken(
            UserAccount user
    ) {

        Instant now = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer("nexio-auth-service")
                        .subject(
                                user.getId().toString()
                        )
                        .claim(
                                "role",
                                user.getRole().name()
                        )
                        .claim(
                                "mobile",
                                user.getMobile()
                        )
                        .issuedAt(now)
                        .expiresAt(
                                now.plusSeconds(
                                        ACCESS_TOKEN_SECONDS
                                )
                        )
                        .build();

        JwsHeader header =
                JwsHeader.with(
                                MacAlgorithm.HS256
                        )
                        .type("JWT")
                        .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(
                                header,
                                claims
                        )
                )
                .getTokenValue();
    }

    public long getAccessTokenSeconds() {
        return ACCESS_TOKEN_SECONDS;
    }
}