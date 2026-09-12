package com.showroom.showroom_back.application.port.input.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}