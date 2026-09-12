package com.showroom.showroom_back.domain.shared.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductPrice(BigDecimal value) {

    public ProductPrice {
        Objects.requireNonNull(value, "ProductPrice cannot be null");
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        // Limitar a 2 decimales (centavos)
        if (value.scale() > 2) {
            throw new IllegalArgumentException("Price can have maximum 2 decimal places");
        }
    }

    public static ProductPrice of(BigDecimal value) {
        return new ProductPrice(value);
    }

    public boolean isGreaterThan(ProductPrice other) {
        Objects.requireNonNull(other);
        return this.value.compareTo(other.value) > 0;
    }

    public boolean isLessThan(ProductPrice other) {
        Objects.requireNonNull(other);
        return this.value.compareTo(other.value) < 0;
    }

    public boolean equals(ProductPrice other) {
        Objects.requireNonNull(other);
        return this.value.compareTo(other.value) == 0;
    }

    public ProductPrice multiply(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return new ProductPrice(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    public ProductPrice add(ProductPrice other) {
        Objects.requireNonNull(other);
        return new ProductPrice(this.value.add(other.value));
    }

    @Override
    public String toString() {
        return "$" + value.toString();
    }
}