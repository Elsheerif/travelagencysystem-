package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    /**
     * Sends an SMS message using Twilio API
     * 
     * @param to      the recipient's phone number
     * @param content the message content
     */
    public void sendSMS(String to, String content) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to),  // recipient's phone number
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),  // sender's phone number
                    content  // message content
            ).create();

            // Log the SID of the sent message for tracking purposes
            logger.info("SMS sent successfully! SID: " + message.getSid());
        } catch (Exception e) {
            // Log the error and throw an exception if SMS fails
            logger.error("Failed to send SMS to " + to + ": " + e.getMessage());
            throw new RuntimeException("Failed to send SMS: " + e.getMessage(), e);
        }
    }
}
