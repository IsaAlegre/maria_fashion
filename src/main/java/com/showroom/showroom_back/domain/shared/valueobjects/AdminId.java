package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record AdminId(UUID value) {

    public AdminId {
        Objects.requireNonNull(value, "AdminId cannot be null");
    }

    public static AdminId generate() {
        return new AdminId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}