package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.DeleteProductInputPort;
import com.showroom.showroom_back.application.port.output.PhotoRepositoryPort;
import com.showroom.showroom_back.application.port.output.PhotoStoragePort;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteProductUseCase implements DeleteProductInputPort {

    private final ProductRepositoryPort productRepository;
    private final PhotoRepositoryPort photoRepository;
    private final PhotoStoragePort photoStorage;

    @Override
    public void deleteProduct(ProductId id){
        Product product = productRepository.findById(id);
        List<Photo> photos = photoRepository.findByProductId(id);
        photos.forEach(photo -> {
            try {
                photoStorage.deletePhoto(photo.photoUrl());
            } catch (Exception e) {
                log.info("Failed to delete photo from storage: " + photo.photoUrl() + ". Error: " + e.getMessage());
            }
        });

        photoRepository.deleteByProductId(id);
        productRepository.delete(id);
    }
}
