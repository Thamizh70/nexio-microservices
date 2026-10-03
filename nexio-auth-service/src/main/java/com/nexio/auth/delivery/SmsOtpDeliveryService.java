package com.nexio.auth.delivery;

import com.nexio.auth.enums.OtpChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsOtpDeliveryService
        implements OtpDeliveryService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    SmsOtpDeliveryService.class
            );

    @Override
    public OtpChannel getChannel() {
        return OtpChannel.SMS;
    }

    @Override
    public void sendOtp(
            String mobile,
            String otp
    ) {

        // Development implementation.
        // Replace later with real SMS provider.

        log.info(
                "NEXIO SMS OTP | mobile={} | otp={}",
                mobile,
                otp
        );
    }
}