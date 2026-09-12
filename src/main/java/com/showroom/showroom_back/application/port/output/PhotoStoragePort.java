package com.showroom.showroom_back.application.port.output;

import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoStoragePort {
    PhotoUrl uploadPhoto(MultipartFile file) throws IOException;
    void deletePhoto(PhotoUrl photoUrl) throws IOException;
    boolean isValidImageFile(MultipartFile file);
}
