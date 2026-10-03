package com.nexio.auth.delivery;

import com.nexio.auth.enums.OtpChannel;

public interface OtpDeliveryService {

    OtpChannel getChannel();

    void sendOtp(
            String mobile,
            String otp
    );
}