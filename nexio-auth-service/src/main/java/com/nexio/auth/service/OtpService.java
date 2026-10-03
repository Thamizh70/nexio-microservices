package com.nexio.auth.service;

import com.nexio.auth.delivery.OtpDeliveryService;
import com.nexio.auth.entity.OtpVerification;
import com.nexio.auth.enums.OtpChannel;
import com.nexio.auth.enums.OtpPurpose;
import com.nexio.auth.enums.OtpStatus;
import com.nexio.auth.repository.OtpVerificationRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OtpService {

	private static final int OTP_EXPIRY_MINUTES = 5;

	private static final int MAX_ATTEMPTS = 5;

	private final OtpVerificationRepository otpRepository;

	private final PasswordEncoder passwordEncoder;

	private final List<OtpDeliveryService> deliveryServices;

	private final SecureRandom secureRandom = new SecureRandom();

	public OtpService(OtpVerificationRepository otpRepository, PasswordEncoder passwordEncoder,
			List<OtpDeliveryService> deliveryServices) {
		this.otpRepository = otpRepository;
		this.passwordEncoder = passwordEncoder;
		this.deliveryServices = deliveryServices;
	}

	@Transactional
	public void sendOtp(String mobile, OtpChannel channel, OtpPurpose purpose) {

		String otp = generateOtp();

		String otpHash = passwordEncoder.encode(otp);

		OtpVerification verification = new OtpVerification();

		verification.setMobile(mobile);
		verification.setOtpHash(otpHash);
		verification.setChannel(channel);
		verification.setPurpose(purpose);
		verification.setStatus(OtpStatus.SENT);
		verification.setExpiresAt(Instant.now().plus(OTP_EXPIRY_MINUTES, ChronoUnit.MINUTES));
		verification.setAttemptCount(0);

		otpRepository.save(verification);

		OtpDeliveryService deliveryService = deliveryServices.stream()
				.filter(service -> service.getChannel() == channel).findFirst()
				.orElseThrow(() -> new IllegalStateException("OTP channel not supported: " + channel));

		deliveryService.sendOtp(mobile, otp);
	}

	@Transactional
	public void verifyOtp(String mobile, String otp, OtpPurpose purpose) {

		OtpVerification verification = otpRepository
				.findTopByMobileAndPurposeAndStatusOrderByCreatedAtDesc(mobile, purpose, OtpStatus.SENT)
				.orElseThrow(() -> new IllegalArgumentException("OTP not found"));

		if (verification.getExpiresAt().isBefore(Instant.now())) {

			verification.setStatus(OtpStatus.EXPIRED);

			otpRepository.save(verification);

			throw new IllegalArgumentException("OTP expired");
		}

		if (verification.getAttemptCount() >= MAX_ATTEMPTS) {

			verification.setStatus(OtpStatus.FAILED);

			otpRepository.save(verification);

			throw new IllegalArgumentException("Maximum OTP attempts exceeded");
		}

		verification.setAttemptCount(verification.getAttemptCount() + 1);

		if (!passwordEncoder.matches(otp, verification.getOtpHash())) {

			otpRepository.save(verification);

			throw new IllegalArgumentException("Invalid OTP");
		}

		verification.setStatus(OtpStatus.VERIFIED);

		verification.setVerifiedAt(Instant.now());

		otpRepository.save(verification);
	}

	private String generateOtp() {

		int number = secureRandom.nextInt(900000) + 100000;

		return String.valueOf(number);
	}
}