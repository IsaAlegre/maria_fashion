package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;

public record PhotoUrl(String value) {

    public PhotoUrl {
        Objects.requireNonNull(value, "PhotoUrl cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("PhotoUrl cannot be blank");
        }
        if (!isValidUrl(value)) {
            throw new IllegalArgumentException("PhotoUrl must be a valid HTTP(S) URL");
        }
    }

    public static PhotoUrl of(String url) {
        return new PhotoUrl(url);
    }

    private static boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public boolean isCloudinaryUrl() {
        return value.contains("cloudinary.com");
    }

    public boolean isSecure() {
        return value.startsWith("https://");
    }

    @Override
    public String toString() {
        return value;
    }
}
