package com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper;

import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.PhotoEntity;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhotoMapper {

    public Photo toDomain(PhotoEntity entity){
        if(entity == null){
            return null;
        }

        log.info("Mapping PhotoEntity to Photo domain object: " + entity);

        return new Photo(
                entity.getId(),
                new ProductId(entity.getProduct().getId()),
                PhotoUrl.of(entity.getPhotoUrl()),
                entity.getCreatedAt()
        );
    }

    public PhotoEntity toEntity(Photo domain, ProductEntity productEntity) {
        if (domain == null) {
            return null;
        }

        log.debug("Mapping Photo domain object to PhotoEntity: {}", domain.id());

        return PhotoEntity.builder()
                .id(domain.id())
                .product(productEntity)
                .photoUrl(domain.photoUrl().value())  // Extraer el String del PhotoUrl
                .createdAt(domain.createdAt())
                .build();
    }

}
