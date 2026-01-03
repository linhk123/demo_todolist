package com.uc_modul4.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtProvider {
    private final String JWT_SECRET = "YourComplexSecretKeyHereYourComplexSecretKeyHere";
    private final long JWT_EXPIRATION = 604800000L; // 7 ngày

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getIdFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getId();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generateRefreshToken(String oldToken) {
        // Logic tạo token mới từ token cũ (nếu token cũ còn hạn hoặc thuộc blacklist hợp lệ)
        return generateToken(Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(oldToken).getBody().getSubject());
    }
    // Thêm vào trong class JwtProvider hiện tại của bạn
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}