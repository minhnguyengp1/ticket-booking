package org.example.ticketbooking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // Generate JWT token
    public String generateToken(Map<String, Object> claims, long expirationMillis) {
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)  // Thêm các claims
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis)) // Thời gian hết hạn
                .signWith(key) // Sử dụng SecretKeySpec để ký
                .compact();
    }

    // Parse token để lấy Claims
    public Claims extractClaims(String token) {
        // Chuyển đổi secretKey thành SecretKeySpec (Key)
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder()
                .setSigningKey(key)  // Xác thực bằng khóa bí mật
                .build()
                .parseClaimsJws(token)  // Giải mã JWT và lấy Claims
                .getBody();
    }
}
