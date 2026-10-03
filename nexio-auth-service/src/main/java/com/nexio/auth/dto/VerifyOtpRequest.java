package com.nexio.auth.dto;

import com.nexio.auth.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyOtpRequest(

        @NotBlank
        String mobile,

        @NotBlank
        String otp,

        @NotNull
        OtpPurpose purpose,

        @NotNull
        com.nexio.auth.enums.UserRole role
) {
}