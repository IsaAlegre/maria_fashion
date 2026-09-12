package com.showroom.showroom_back.domain.common.exception;

import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(ProductId productId) {
        super("Product not found with id: " + productId);
    }
}