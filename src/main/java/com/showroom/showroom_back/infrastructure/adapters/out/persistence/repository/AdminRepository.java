package com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository;

import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
    @Query(
            value = "SELECT * FROM admins where email = ?1",
            nativeQuery = true
    )
    Optional<AdminEntity> findByEmail(String email);

    @Query(
            value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM admins WHERE email = ?1",
            nativeQuery = true
    )
    boolean existsByEmail(String email);
}
