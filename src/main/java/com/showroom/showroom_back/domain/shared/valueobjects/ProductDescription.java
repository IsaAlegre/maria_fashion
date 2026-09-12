package com.showroom.showroom_back.domain.shared.valueobjects;

public record ProductDescription(String value) {

    public ProductDescription {
        if (value != null && value.length() > 2000) {
            throw new IllegalArgumentException("ProductDescription cannot exceed 2000 characters");
        }
    }

    public static ProductDescription of(String description) {
        if (description == null || description.isBlank()) {
            return new ProductDescription(null);
        }
        return new ProductDescription(description.trim());
    }

    public static ProductDescription empty() {
        return new ProductDescription(null);
    }

    public boolean isEmpty() {
        return value == null || value.isBlank();
    }

    @Override
    public String toString() {
        return value != null ? value : "";
    }
}