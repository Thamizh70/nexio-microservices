package com.nexio.auth.entity;

import com.nexio.auth.enums.OtpChannel;
import com.nexio.auth.enums.OtpPurpose;
import com.nexio.auth.enums.OtpStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "otp_verifications", indexes = { @Index(name = "idx_otp_mobile", columnList = "mobile") })
public class OtpVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 20)
	private String mobile;

	@Column(nullable = false)
	private String otpHash;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OtpChannel channel;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OtpPurpose purpose;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OtpStatus status;

	@Column(nullable = false)
	private Instant expiresAt;

	private Instant verifiedAt;

	@Column(nullable = false)
	private int attemptCount;

	@Column(nullable = false)
	private Instant createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOtpHash() {
		return otpHash;
	}

	public void setOtpHash(String otpHash) {
		this.otpHash = otpHash;
	}

	public OtpChannel getChannel() {
		return channel;
	}

	public void setChannel(OtpChannel channel) {
		this.channel = channel;
	}

	public OtpPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(OtpPurpose purpose) {
		this.purpose = purpose;
	}

	public OtpStatus getStatus() {
		return status;
	}

	public void setStatus(OtpStatus status) {
		this.status = status;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Instant getVerifiedAt() {
		return verifiedAt;
	}

	public void setVerifiedAt(Instant verifiedAt) {
		this.verifiedAt = verifiedAt;
	}

	public int getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}