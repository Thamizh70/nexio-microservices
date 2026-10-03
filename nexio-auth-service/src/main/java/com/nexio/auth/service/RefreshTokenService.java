package com.nexio.auth.service;

import com.nexio.auth.entity.RefreshToken;
import com.nexio.auth.entity.UserAccount;
import com.nexio.auth.repository.RefreshTokenRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private static final int REFRESH_TOKEN_DAYS = 30;

    private final RefreshTokenRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom =
            new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String createRefreshToken(
            UserAccount user
    ) {

        String rawToken =
                generateToken();

        RefreshToken entity =
                new RefreshToken();

        entity.setUserId(user.getId());

        entity.setTokenHash(
                passwordEncoder.encode(rawToken)
        );

        entity.setExpiresAt(
                Instant.now()
                        .plus(
                                REFRESH_TOKEN_DAYS,
                                ChronoUnit.DAYS
                        )
        );

        entity.setRevoked(false);

        repository.save(entity);

        return rawToken;
    }

    private String generateToken() {

        byte[] bytes = new byte[64];

        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}