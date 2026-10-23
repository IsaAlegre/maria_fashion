package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.UploadPhotoInputPort;
import com.showroom.showroom_back.application.port.output.PhotoRepositoryPort;
import com.showroom.showroom_back.application.port.output.PhotoStoragePort;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.common.exception.InvalidPhotoFileException;
import com.showroom.showroom_back.domain.photo.Photo;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UploadPhotoUseCase implements UploadPhotoInputPort {

    private final ProductRepositoryPort productRepository;
    private final PhotoRepositoryPort photoRepository;
    private final PhotoStoragePort photoStorage;

    @Override
    public PhotoUrl uploadPhoto(ProductId productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId);

        if(!photoStorage.isValidImageFile(file)) {
            throw new InvalidPhotoFileException("Invalido el tipo de archivo. Solo se permiten imágenes JPEG, PNG o GIF.");
        }

        PhotoUrl photoUrl = photoStorage.uploadPhoto(file);
        Photo photo = Photo.create(productId, photoUrl.value());
        photoRepository.save(photo);
        return photoUrl;
    }
}
