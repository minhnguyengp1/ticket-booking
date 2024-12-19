package org.example.ticketbooking.service;

import org.example.ticketbooking.model.AuthCredentials;
import org.example.ticketbooking.model.User;
import org.example.ticketbooking.repository.AuthRepository;
import org.example.ticketbooking.utils.AuthUtils;
import org.example.ticketbooking.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtUtil jwtUtil; // Autowire JwtUtil

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Đăng nhập
    public Map<String, Object> login(AuthCredentials loginData) throws Exception {
        User user = authRepository.findByEmail(loginData.getEmail())
                .orElseThrow(() -> new Exception("User not found"));

        System.out.println("user: " + user);

        if (!AuthUtils.matchesHash(loginData.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(Map.of("email", user.getEmail()), 86400000);  // 1 day

// Trả về cả token và thông tin người dùng
        Map<String, Object> response = Map.of(
                "token", token,
                "user", user
        );

        return response;
    }

    // Đăng ký
    public String register(AuthCredentials registerData) throws Exception {
        if (!AuthUtils.isValidEmail(registerData.getEmail())) {
            throw new Exception("Invalid email address");
        }

        String hashedPassword = passwordEncoder.encode(registerData.getPassword());
        User user = new User(registerData.getEmail(), hashedPassword);
        authRepository.save(user);

        return "Registration successful";
    }
}