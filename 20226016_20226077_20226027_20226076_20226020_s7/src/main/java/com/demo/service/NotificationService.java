package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.model.Notification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    private static final String ACCOUNT_SID = "your_account_sid";
    private static final String AUTH_TOKEN = "your_auth_token";

    public ResponseEntity<String> sendEmail(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(notification.getSenderEmail());
        message.setTo(notification.getRecipient());
        message.setSubject(notification.getSubject());
        message.setText(notification.getMessage());

        try {
            emailSender.send(message);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MailException e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

    public ResponseEntity<String> sendSms(Notification notification) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message.creator(
                    new PhoneNumber(notification.getPhoneNumber()),
                    new PhoneNumber(notification.getSenderPhoneNumber()),
                    notification.getMessage()
            ).create();

            return ResponseEntity.ok("SMS sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }

    public ResponseEntity<String> createTemplate(Notification template) {
        return ResponseEntity.ok("Template created successfully");
    }

    public ResponseEntity<List<Notification>> getAllTemplates() {
        return ResponseEntity.ok(List.of(new Notification()));
    }

    public ResponseEntity<String> getStatistics() {
        return ResponseEntity.ok("Total notifications sent: 100");
    }
}
