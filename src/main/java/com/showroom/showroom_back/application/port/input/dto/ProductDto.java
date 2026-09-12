package com.showroom.showroom_back.application.port.input.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String status,
        List<String> photoUrls,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}