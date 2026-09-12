package com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository;

import com.showroom.showroom_back.domain.product.ProductStatus;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByStatus(ProductStatus status);
    @Query(
            value = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER('%' || ?1 || '%')",
            nativeQuery = true
    )
    List<ProductEntity> searchByName(@Param("name") String name);

    @Query(
            value = "SELECT * FROM products WHERE status = ?1 AND stock_quantity > 0",
            nativeQuery = true
    )
    List<ProductEntity> findActiveWithStock(String status); //Productos con stock y activos

    long countByStatus(ProductStatus status); //Contar productos por estado
    boolean existsByName(String name);
}
