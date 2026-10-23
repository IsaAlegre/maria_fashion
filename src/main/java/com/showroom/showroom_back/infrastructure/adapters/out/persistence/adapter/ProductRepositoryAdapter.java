package com.showroom.showroom_back.infrastructure.adapters.out.persistence.adapter;

import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.common.exception.ProductNotFoundException;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.entity.ProductEntity;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.mapper.ProductMapper;
import com.showroom.showroom_back.infrastructure.adapters.out.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product findById(ProductId id) {
        log.debug("Finding product by id: {}", id.value());

        return productRepository.findById(id.value())
                .map(entity -> {
                    log.debug("Product found: {}", id.value());
                    return productMapper.toDomain(entity);
                })
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id.value());
                    return new ProductNotFoundException(id.value().toString());
                });
    }

    public List<Product> findAll(){
        List<ProductEntity> entities = productRepository.findAll();
        List<Product> products = entities.stream()
                .map(productMapper::toDomain)
                .toList();
        log.debug("Found {} products", products.size());
        return products;
    }

    public Product save(Product product){
        ProductEntity entity= productMapper.toEntity(product);
        ProductEntity saved = productRepository.save(entity);
        Product savedProduct = productMapper.toDomain(saved);

        log.info("Product saved successfully with id: {}", savedProduct.id().value());
        return savedProduct;

    }

    public void delete(ProductId id){
        log.debug("Deleting product with id: {}", id.value());
        if(!productRepository.existsById(id.value())){
            log.warn("Product not found with id: {}", id.value());
            throw new ProductNotFoundException(id);  // ✅ Así, sin .value()
        }
        productRepository.deleteById(id.value());
        log.info("Product deleted successfully with id: {}", id.value());
    }

    @Override
    public boolean existsById(ProductId id) {
        return productRepository.existsById(id.value());
    }
}
