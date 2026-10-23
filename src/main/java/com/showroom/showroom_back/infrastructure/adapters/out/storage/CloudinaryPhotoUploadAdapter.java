package com.showroom.showroom_back.infrastructure.adapters.out.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.showroom.showroom_back.application.port.output.PhotoStoragePort;
import com.showroom.showroom_back.domain.shared.valueobjects.PhotoUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryPhotoUploadAdapter implements PhotoStoragePort {

    private final Cloudinary cloudinary;

    public CloudinaryPhotoUploadAdapter(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Override
    public PhotoUrl uploadPhoto(MultipartFile file) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String secureUrl = (String) result.get("secure_url");
        return PhotoUrl.of(secureUrl);
    }

    @Override
    public void deletePhoto(PhotoUrl photoUrl) throws IOException {
        cloudinary.uploader().destroy(extractPublicId(photoUrl.value()), ObjectUtils.emptyMap());
    }

    @Override
    public boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return file != null
                && !file.isEmpty()
                && contentType != null
                && contentType.startsWith("image/");
    }


    private String extractPublicId(String url) {
        String afterUpload = url.substring(url.indexOf("/upload/") + "/upload/".length());
        String withoutVersion = afterUpload.replaceFirst("^v\\d+/", "");
        int dot = withoutVersion.lastIndexOf('.');
        return dot > 0 ? withoutVersion.substring(0, dot) : withoutVersion;
    }
}
