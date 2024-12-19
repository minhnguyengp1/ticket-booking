package org.example.ticketbooking.controller;

import org.example.ticketbooking.model.AuthCredentials;
import org.example.ticketbooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthCredentials loginData) {
        try {
            System.out.println("Login Data: " + loginData.getEmail() + ", " + loginData.getPassword());  // Debugging

            Map<String, Object> response = authService.login(loginData);

            // Trả về cả token và user
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthCredentials registerData) {
        try {
            String result = authService.register(registerData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
