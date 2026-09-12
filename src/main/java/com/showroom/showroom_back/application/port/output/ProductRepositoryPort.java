package com.showroom.showroom_back.application.port.output;

import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

import java.util.List;

public interface ProductRepositoryPort {
    Product findById(ProductId id);
    List<Product> findAll();
    Product save(Product product);
    void delete(ProductId id);
    boolean existsById(ProductId id);
}
