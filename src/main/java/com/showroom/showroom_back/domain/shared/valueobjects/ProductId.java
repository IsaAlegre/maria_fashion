package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId {
        Objects.requireNonNull(value, "ProductId cannot be null");
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
