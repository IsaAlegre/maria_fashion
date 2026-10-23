package com.showroom.showroom_back.infrastructure.config;

import com.showroom.showroom_back.application.port.output.JwtTokenPort;
import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider implements JwtTokenPort {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${jwt.expiration:86400000}")  // 24 horas por defecto
    private long tokenExpirationMs;


    @Override
    public String generateToken(Admin admin) {
        log.debug("Generating JWT token for admin: {}", admin.email().value());

        try {
            Instant now = Instant.now();
            Instant expiresAt = now.plus(tokenExpirationMs / 1000, ChronoUnit.SECONDS);

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(expiresAt)
                    .subject(admin.email().value())
                    .claim("adminId", admin.id().value().toString())
                    .claim("role", "ADMIN")
                    .build();

            String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            log.info("JWT token generated successfully for admin: {}", admin.email().value());
            return token;
        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    @Override
    public boolean isValid(String token) {
        try {
            jwtDecoder.decode(token);
            log.debug("JWT token is valid");
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }


    @Override
    public AdminEmail getEmailFromToken(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            String email = jwt.getSubject();
            log.debug("Extracted email from JWT token: {}", email);
            return AdminEmail.of(email);
        } catch (Exception e) {
            log.error("Error extracting email from JWT token", e);
            throw new RuntimeException("Error extracting email from JWT token", e);
        }
    }
}