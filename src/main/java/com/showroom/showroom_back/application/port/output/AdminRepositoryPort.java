package com.showroom.showroom_back.application.port.output;

import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminId;

public interface AdminRepositoryPort {

    Admin findByEmail(AdminEmail email);
    Admin save(Admin admin);
    Admin findById(AdminId id);
    boolean existsById(AdminId id);
    boolean existsByEmail(AdminEmail email);
}
