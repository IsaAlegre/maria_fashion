package com.showroom.showroom_back.application.port.input.dto;

import java.util.Objects;

public record AuthenticateAdminCommand(
        String email,
        String password
) {

    public AuthenticateAdminCommand {
        Objects.requireNonNull(email, "Email is required");
        Objects.requireNonNull(password, "Password is required");

        if (email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
    }
}
