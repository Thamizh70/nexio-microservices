package com.nexio.auth.repository;

import com.nexio.auth.entity.OtpVerification;
import com.nexio.auth.enums.OtpPurpose;
import com.nexio.auth.enums.OtpStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpVerificationRepository
        extends JpaRepository<OtpVerification, UUID> {

    Optional<OtpVerification>
    findTopByMobileAndPurposeAndStatusOrderByCreatedAtDesc(
            String mobile,
            OtpPurpose purpose,
            OtpStatus status
    );
}