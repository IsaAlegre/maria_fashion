package com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository;

import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, UUID> {

    List<PhotoEntity> findByProductId(UUID productId);
    @Query(
            value = "SELECT COUNT(*) FROM product_photos WHERE product_id = ?1",
            nativeQuery = true
    )
    long countByProductId(UUID productId);
    void deleteByProductId(UUID productId);
    @Query(
            value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM product_photos WHERE product_id = ?1",
            nativeQuery = true
    )
    boolean existsByProductId(UUID productId);
}
