package com.nexio.auth.controller;

import com.nexio.auth.dto.AuthResponse;
import com.nexio.auth.dto.SendOtpRequest;
import com.nexio.auth.dto.VerifyOtpRequest;
import com.nexio.auth.service.AuthService;
import com.nexio.auth.service.OtpService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final OtpService otpService;

	private final AuthService authService;

	public AuthController(OtpService otpService, AuthService authService) {
		this.otpService = otpService;
		this.authService = authService;
	}

	@PostMapping("/otp/send")
	public ResponseEntity<?> sendOtp(@Valid @RequestBody SendOtpRequest request) {

		otpService.sendOtp(request.mobile(), request.channel(), request.purpose());

		return ResponseEntity.ok(Map.of("message", "OTP sent successfully",

				"channel", request.channel(),

				"expiresIn", 300));
	}

	@PostMapping("/otp/verify")
	public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {

		AuthResponse response = authService.verifyOtpAndLogin(request.mobile(), request.otp(), request.purpose(),
				request.role());

		return ResponseEntity.ok(response);
	}
}