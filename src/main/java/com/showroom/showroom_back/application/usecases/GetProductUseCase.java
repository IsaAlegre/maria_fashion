package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.GetProductInputPort;
import com.showroom.showroom_back.application.port.input.dto.ProductDto;
import com.showroom.showroom_back.application.port.output.PhotoRepositoryPort;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetProductUseCase implements GetProductInputPort {

    private final ProductRepositoryPort productRepositoryPort;
    private final PhotoRepositoryPort photoRepositoryPort;

    @Override
    public ProductDto getProduct(ProductId id){
        Product product = productRepositoryPort.findById(id);
        List<Photo> photos = photoRepositoryPort.findByProductId(id);
        List<String> photoUrls = photos.stream()
                .map(photo -> photo.photoUrl().value())
                .toList();

        return new ProductDto(
                product.id().value().toString(),
                product.name().value(),
                product.description().value(),
                product.price().value(),
                product.stock().quantity(),
                product.status().name(),
                photoUrls,
                product.createdAt(),
                product.updatedAt()
        );
    }
}
