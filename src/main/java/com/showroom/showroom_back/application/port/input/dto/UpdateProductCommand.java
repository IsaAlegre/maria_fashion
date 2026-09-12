package com.showroom.showroom_back.application.port.input.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record UpdateProductCommand(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {
    public UpdateProductCommand {
        Objects.requireNonNull(name, "Name is required");
        Objects.requireNonNull(price, "Price is required");
        Objects.requireNonNull(stockQuantity, "Stock quantity is required");
    }
}