package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadPhotoInputPort {
    PhotoUrl upload(ProductId id, MultipartFile file)
        throws IOException;
}
