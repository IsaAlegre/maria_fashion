package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;

public record AdminPassword(String hashedValue) {

    public AdminPassword {
        Objects.requireNonNull(hashedValue, "AdminPassword cannot be null");
        if (hashedValue.isBlank()) {
            throw new IllegalArgumentException("AdminPassword cannot be blank");
        }
    }

    @Override
    public String toString() {
        return "***REDACTED***";
    }
}
