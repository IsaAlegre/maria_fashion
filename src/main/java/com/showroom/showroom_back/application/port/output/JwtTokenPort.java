package com.showroom.showroom_back.application.port.output;

import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;

public interface JwtTokenPort {

    String generateToken(Admin admin);

    boolean isValid(String token);

    AdminEmail getEmailFromToken(String token);
}
