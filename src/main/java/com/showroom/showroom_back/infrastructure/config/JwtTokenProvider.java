package com.showroom.showroom_back.infrastructure.config;

import com.showroom.showroom_back.application.port.output.JwtTokenPort;
import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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

    @Value("${jwt.expiration:86400000}")
    private long tokenExpirationMs;

    @Override
    public String generateToken(Admin admin) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(tokenExpirationMs / 1000, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(admin.email().value())
                .claim("adminId", admin.id().value().toString())
                .claim("role", "ADMIN")
                .build();

        // El header indica el algoritmo HS256: sin esto el encoder no puede elegir la clave
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        log.info("JWT generated for admin: {}", admin.email().value());
        return token;
    }

    @Override
    public boolean isValid(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public AdminEmail getEmailFromToken(String token) {
        String email = jwtDecoder.decode(token).getSubject();
        return AdminEmail.of(email);
    }
}