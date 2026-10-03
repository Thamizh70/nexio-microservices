package com.nexio.auth.service;

import com.nexio.auth.dto.AuthResponse;
import com.nexio.auth.entity.UserAccount;
import com.nexio.auth.enums.OtpPurpose;
import com.nexio.auth.enums.UserRole;
import com.nexio.auth.repository.UserAccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final OtpService otpService;

    private final UserAccountRepository userRepository;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    public AuthService(
            OtpService otpService,
            UserAccountRepository userRepository,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public AuthResponse verifyOtpAndLogin(
            String mobile,
            String otp,
            OtpPurpose purpose,
            UserRole requestedRole
    ) {

        otpService.verifyOtp(
                mobile,
                otp,
                purpose
        );

        UserAccount user =
                userRepository
                        .findByMobile(mobile)
                        .orElseGet(() ->
                                createUser(
                                        mobile,
                                        requestedRole
                                )
                        );

        if (!user.isActive()) {
            throw new IllegalStateException(
                    "User account is inactive"
            );
        }

        user.setMobileVerified(true);

        userRepository.save(user);

        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenSeconds()
        );
    }

    private UserAccount createUser(
            String mobile,
            UserRole role
    ) {

        UserAccount user =
                new UserAccount();

        user.setMobile(mobile);
        user.setRole(role);
        user.setMobileVerified(true);
        user.setActive(true);

        return userRepository.save(user);
    }
}