package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;

public record ProductName(String value) {

    public ProductName {
        Objects.requireNonNull(value, "ProductName cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("ProductName cannot be blank");
        }
        if (value.length() < 3) {
            throw new IllegalArgumentException("ProductName must be at least 3 characters");
        }
        if (value.length() > 255) {
            throw new IllegalArgumentException("ProductName cannot exceed 255 characters");
        }
    }

    public static ProductName of(String name) {
        return new ProductName(name.trim());
    }

    @Override
    public String toString() {
        return value;
    }
}