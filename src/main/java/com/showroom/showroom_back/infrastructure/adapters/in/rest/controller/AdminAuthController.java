package com.showroom.showroom_back.infrastructure.adapters.in.rest.controller;

import com.showroom.showroom_back.application.port.input.AuthenticateAdminInputPort;
import com.showroom.showroom_back.application.port.input.dto.AuthenticateAdminCommand;
import com.showroom.showroom_back.infrastructure.adapters.in.rest.dto.request.LoginRequest;
import com.showroom.showroom_back.infrastructure.adapters.in.rest.dto.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthenticateAdminInputPort authenticateAdminInputPort;

    @Value("${jwt.expiration:86400000}")
    private long tokenExpirationMs;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authenticateAdminInputPort.execute(
                new AuthenticateAdminCommand(request.email(), request.password())
        );
        return ResponseEntity.ok(new LoginResponse(token, tokenExpirationMs));
    }
}