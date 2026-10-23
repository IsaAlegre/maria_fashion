package com.showroom.showroom_back.infrastructure.adapters.out.persistence.adapter;

import com.showroom.showroom_back.application.port.output.AdminRepositoryPort;
import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminId;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import com.showroom.showroom_back.domain.common.exception.AdminNotFoundException;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.AdminEntity;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper.AdminMapper;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class AdminRepositoryAdapter implements AdminRepositoryPort {

    private final AdminRepository jpaRepository;
    private final AdminMapper mapper;

    @Override
    public Admin findByEmail(AdminEmail email) {
        log.debug("Finding admin by email: {}", email.value());

        return jpaRepository.findByEmail(email.value())
                .map(entity -> {
                    log.debug("Admin found: {}", email.value());
                    return mapper.toDomain(entity);
                })
                .orElseThrow(() -> {
                    log.warn("Admin not found with email: {}", email.value());
                    return new AdminNotFoundException("Admin not found with email: " + email.value());
                });
    }

    @Override
    public Admin findById(AdminId id) {
        log.debug("Finding admin by id: {}", id.value());

        return jpaRepository.findById(id.value())
                .map(entity -> {
                    log.debug("Admin found: {}", id.value());
                    return mapper.toDomain(entity);
                })
                .orElseThrow(() -> {
                    log.warn("Admin not found with id: {}", id.value());
                    return new AdminNotFoundException("Admin not found with id: " + id.value());
                });
    }

    @Override
    public Admin save(Admin admin) {
        log.debug("Saving admin: {}", admin.email().value());

        try {
            // Domain → JPA Entity
            AdminEntity entity = mapper.toEntity(admin);

            // Guardar en BD
            AdminEntity saved = jpaRepository.save(entity);

            // JPA Entity → Domain
            Admin savedAdmin = mapper.toDomain(saved);

            log.info("Admin saved successfully with id: {}", savedAdmin.id().value());
            return savedAdmin;
        } catch (Exception e) {
            log.error("Error saving admin: {}", admin.email().value(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByEmail(AdminEmail email) {
        log.debug("Checking if admin exists with email: {}", email.value());

        boolean exists = jpaRepository.existsByEmail(email.value());

        log.debug("Admin exists: {} for email: {}", exists, email.value());
        return exists;
    }

    @Override
    public boolean existsById(AdminId id) {
        log.debug("Checking if admin exists with id: {}", id.value());

        boolean exists = jpaRepository.existsById(id.value());

        log.debug("Admin exists: {} for id: {}", exists, id.value());
        return exists;
    }
}
