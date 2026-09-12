package com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper;

import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminId;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminPassword;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.AdminEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminMapper {
    public Admin toDomain(AdminEntity entity) {
        if (entity == null) {
            return null;
        }

        log.debug("Mapping AdminEntity to Admin domain object: {}", entity.getEmail());

        return new Admin(
                new AdminId(entity.getId()),
                AdminEmail.of(entity.getEmail()),
                new AdminPassword(entity.getPassword()),
                entity.getCreatedAt(),
                entity.getLastLogin()
        );
    }

    public AdminEntity toEntity(Admin domain) {
        if (domain == null) {
            return null;
        }

        log.debug("Mapping Admin domain object to AdminEntity: {}", domain.email());

        return AdminEntity.builder()
                .id(domain.id().value())
                .email(domain.email().value())
                .password(domain.password().hashedValue())
                .createdAt(domain.createdAt())
                .lastLogin(domain.lastLogin())
                .build();
    }

}
