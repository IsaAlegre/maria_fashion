package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.GetAllProductsInputPort;
import com.showroom.showroom_back.application.port.input.dto.ProductDto;
import com.showroom.showroom_back.application.port.output.PhotoRepositoryPort;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllProductsUseCase implements GetAllProductsInputPort {

    private final ProductRepositoryPort productRepository;
    private final PhotoRepositoryPort photoRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    List<Photo> photos = photoRepository.findByProductId((product.id()));
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
                })
                .toList();
    }
}
