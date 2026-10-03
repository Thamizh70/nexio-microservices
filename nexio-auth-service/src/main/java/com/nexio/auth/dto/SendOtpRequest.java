package com.nexio.auth.dto;

import com.nexio.auth.enums.OtpChannel;
import com.nexio.auth.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendOtpRequest(

		@NotBlank String mobile,

		@NotNull OtpChannel channel,

		@NotNull OtpPurpose purpose) {
}