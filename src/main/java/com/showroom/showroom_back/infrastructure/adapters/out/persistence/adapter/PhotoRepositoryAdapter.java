package com.showroom.showroom_back.infrastructure.adapters.out.persistence.adapter;

import com.showroom.showroom_back.application.port.output.PhotoRepositoryPort;
import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import com.showroom.showroom_back.domain.common.exception.PhotoNotFoundException;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.PhotoEntity;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.ProductEntity;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper.PhotoMapper;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository.PhotoRepository;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhotoRepositoryAdapter implements PhotoRepositoryPort {

    private final PhotoRepository jpaRepository;
    private final ProductRepository productJpaRepository;
    private final PhotoMapper mapper;

    @Override
    public Photo save(Photo photo) {
        log.debug("Saving photo for product: {}", photo.productId().value());

        // ⭐ Obtener la entidad del producto (necesaria para la relación)
        ProductEntity productEntity = productJpaRepository.findById(photo.productId().value())
                .orElseThrow(() -> {
                    log.warn("Product not found for photo: {}", photo.productId().value());
                    return new PhotoNotFoundException("Product not found");
                });

        // Domain → JPA Entity
        PhotoEntity entity = mapper.toEntity(photo, productEntity);

        // Guardar en BD
        PhotoEntity saved = jpaRepository.save(entity);

        // JPA Entity → Domain
        Photo savedPhoto = mapper.toDomain(saved);

        log.info("Photo saved successfully with id: {}", savedPhoto.id());
        return savedPhoto;
    }

    @Override
    public Photo findById(UUID id) {
        log.debug("Finding photo by id: {}", id);

        return jpaRepository.findById(id)
                .map(entity -> {
                    log.debug("Photo found: {}", id);
                    return mapper.toDomain(entity);
                })
                .orElseThrow(() -> {
                    log.warn("Photo not found with id: {}", id);
                    return new PhotoNotFoundException("Photo not found with id: " + id);
                });
    }

    @Override
    public List<Photo> findByProductId(ProductId productId) {
        log.debug("Finding photos for product: {}", productId.value());

        List<PhotoEntity> entities = jpaRepository.findByProductId(productId.value());

        List<Photo> photos = entities.stream()
                .map(mapper::toDomain)
                .toList();

        log.info("Retrieved {} photos for product: {}", photos.size(), productId.value());
        return photos;
    }

    @Override
    public void delete(UUID id) {
        log.debug("Deleting photo with id: {}", id);

        if (!jpaRepository.existsById(id)) {
            log.warn("Photo not found with id: {}", id);
            throw new PhotoNotFoundException("Photo not found with id: " + id);
        }

        jpaRepository.deleteById(id);
        log.info("Photo deleted successfully with id: {}", id);
    }

    @Override
    public void deleteByProductId(ProductId productId) {
        log.debug("Deleting all photos for product: {}", productId.value());

        long count = jpaRepository.countByProductId(productId.value());

        jpaRepository.deleteByProductId(productId.value());

        log.info("Deleted {} photos for product: {}", count, productId.value());
    }

    @Override
    public long countByProductId(ProductId productId) {
        log.debug("Counting photos for product: {}", productId.value());

        long count = jpaRepository.countByProductId(productId.value());

        log.debug("Product {} has {} photos", productId.value(), count);
        return count;
    }

}
