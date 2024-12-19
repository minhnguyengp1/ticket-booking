package org.example.ticketbooking.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class AuthUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Mã hóa mật khẩu
    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    // So sánh mật khẩu đã mã hóa
    public static boolean matchesHash(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

    // Kiểm tra email hợp lệ
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}