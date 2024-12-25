package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.User;
import com.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable int userId) {
        return userService.getUserDetails(userId);
    }

    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<List<String>> getUserDashboard(@PathVariable int userId) {
        return userService.getUserDashboard(userId);
    }
}
