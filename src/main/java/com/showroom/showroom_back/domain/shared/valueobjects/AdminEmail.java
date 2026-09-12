package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;

public record AdminEmail(String value) {

    public AdminEmail {
        Objects.requireNonNull(value, "AdminEmail cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("AdminEmail cannot be blank");
        }
        if (!isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static AdminEmail of(String email) {
        return new AdminEmail(email.toLowerCase().trim());
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    @Override
    public String toString() {
        return value;
    }
}
