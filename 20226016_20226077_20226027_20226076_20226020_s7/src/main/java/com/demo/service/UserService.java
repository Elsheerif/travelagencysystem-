package com.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.model.User;

@Service
public class UserService {

    private final Map<Integer, User> userDatabase = new HashMap<>();
    private int userIdCounter = 1;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseEntity<String> registerUser(User user) {
        // Simple validation for email
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        // Hash the password before storing it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserId(userIdCounter++);
        userDatabase.put(user.getUserId(), user);
        return ResponseEntity.ok("User registered successfully with ID: " + user.getUserId());
    }

    public ResponseEntity<String> loginUser(String email, String password) {
        for (User user : userDatabase.values()) {
            if (user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.ok("Login successful for user ID: " + user.getUserId());
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    public ResponseEntity<User> getUserDetails(int userId) {
        User user = userDatabase.get(userId);
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<String>> getUserDashboard(int userId) {
        if (!userDatabase.containsKey(userId)) {
            return ResponseEntity.status(404).body(null);
        }

        List<String> bookings = new ArrayList<>();
        // Example bookings (this would ideally come from a database or other services)
        bookings.add("Hotel: Hilton Cairo, Date: 2024-12-25");
        bookings.add("Event: Music Festival, Date: 2024-12-26");
        return ResponseEntity.ok(bookings);
    }
}
