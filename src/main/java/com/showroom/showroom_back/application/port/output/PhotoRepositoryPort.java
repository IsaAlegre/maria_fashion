package com.showroom.showroom_back.application.port.output;

import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

import java.util.List;
import java.util.UUID;

public interface PhotoRepositoryPort {

    Photo save(Photo photo);
    List<Photo> findByProductId(ProductId productId);
    Photo findById(UUID photoId);
    void delete(UUID photoId);
    void deleteByProductId(ProductId productId);
    int countByProductId(ProductId productId);
}
