package com.showroom.showroom_back.domain.common.exception;

import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminId;

public class AdminNotFoundException extends DomainException {

    public AdminNotFoundException(AdminEmail email) {
        super("Admin not found with email: " + email);
    }

    public AdminNotFoundException(AdminId id) {
        super("Admin not found with id: " + id);
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}