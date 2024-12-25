package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Notification;
import com.demo.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/template")
    public ResponseEntity<String> createTemplate(@RequestBody Notification template) {
        return notificationService.createTemplate(template);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<Notification>> getAllTemplates() {
        return notificationService.getAllTemplates();
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        if (notification.getType().equalsIgnoreCase("SMS")) {
            return notificationService.sendSms(notification);
        } else if (notification.getType().equalsIgnoreCase("EMAIL")) {
            return notificationService.sendEmail(notification);
        } else {
            return ResponseEntity.badRequest().body("Invalid notification type. Please specify 'SMS' or 'EMAIL'.");
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStatistics() {
        return notificationService.getStatistics();
    }
}
