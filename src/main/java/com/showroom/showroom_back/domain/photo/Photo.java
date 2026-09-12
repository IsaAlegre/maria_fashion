package com.showroom.showroom_back.domain.photo;

import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record Photo(
        UUID id,
        ProductId productId,
        PhotoUrl photoUrl,
        LocalDateTime createdAt
) {

    public Photo {
        Objects.requireNonNull(id, "Photo id cannot be null");
        Objects.requireNonNull(productId, "Photo productId cannot be null");
        Objects.requireNonNull(photoUrl, "Photo photoUrl cannot be null");
        Objects.requireNonNull(createdAt, "Photo createdAt cannot be null");
    }

    public static Photo create(ProductId productId, String photoUrl) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(photoUrl);

        return new Photo(
                UUID.randomUUID(),
                productId,
                PhotoUrl.of(photoUrl),
                LocalDateTime.now()
        );
    }

    public static Photo create(ProductId productId, PhotoUrl photoUrl) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(photoUrl);

        return new Photo(
                UUID.randomUUID(),
                productId,
                photoUrl,
                LocalDateTime.now()
        );
    }

    // Métodos de consulta
    public boolean belongsTo(ProductId otherProductId) {
        return this.productId.equals(otherProductId);
    }

    public boolean isCloudinaryPhoto() {
        return photoUrl.isCloudinaryUrl();
    }
}
