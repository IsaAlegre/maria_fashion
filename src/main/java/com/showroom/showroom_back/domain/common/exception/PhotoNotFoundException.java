package com.showroom.showroom_back.domain.common.exception;

import java.util.UUID;

public class PhotoNotFoundException extends DomainException{

    public PhotoNotFoundException(UUID photoId) {
        super("Photo not found with id: " + photoId);
    }

    public PhotoNotFoundException(String message) {
        super(message);
    }
}
