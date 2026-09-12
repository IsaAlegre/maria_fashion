package com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper;

import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductDescription;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductName;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductPrice;
import com.showroom.showroom_back.domain.shared.valueobjects.Stock;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMapper {

    public Product toDomain(ProductEntity entity){
        if(entity == null) return null;

        log.info("Mapping ProductEntity to Product domain object: {}", entity);
        return new Product(
                new ProductId(entity.getId()),
                ProductName.of(entity.getName()),
                ProductDescription.of(entity.getDescription()),
                ProductPrice.of(entity.getPrice()),
                Stock.of(entity.getStockQuantity()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        log.debug("Mapping Product to ProductJpaEntity: {}", domain.id());

        return ProductEntity.builder()
                .id(domain.id().value())
                .name(domain.name().value())
                .description(domain.description().value())
                .price(domain.price().value())
                .stockQuantity(domain.stock().quantity())
                .status(domain.status())
                .createdAt(domain.createdAt())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
