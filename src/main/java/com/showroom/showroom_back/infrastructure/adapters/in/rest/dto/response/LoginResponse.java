package com.showroom.showroom_back.infrastructure.adapters.in.rest.dto.response;


public record LoginResponse(
        String token,
        long expiresIn
) {
}
