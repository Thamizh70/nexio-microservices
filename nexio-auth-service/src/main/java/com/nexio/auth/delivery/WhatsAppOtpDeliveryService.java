package com.nexio.auth.delivery;

import com.nexio.auth.enums.OtpChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppOtpDeliveryService
        implements OtpDeliveryService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    WhatsAppOtpDeliveryService.class
            );

    @Override
    public OtpChannel getChannel() {
        return OtpChannel.WHATSAPP;
    }

    @Override
    public void sendOtp(
            String mobile,
            String otp
    ) {

        // Development implementation.
        // Replace later with WhatsApp Business/API provider.

        log.info(
                "NEXIO WHATSAPP OTP | mobile={} | otp={}",
                mobile,
                otp
        );
    }
}