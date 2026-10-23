package com.showroom.showroom_back.domain.stock;

import com.showroom.showroom_back.domain.product.Product;


public class StockDomainService {


    public Product reserveStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad a reservar debe ser positiva");
        }
        return product.decreaseStock(quantity);
    }

    public Product releaseStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad a liberar debe ser positiva");
        }
        return product.increaseStock(quantity);
    }


    public Product adjustStock(Product product, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock no puede ser negativo");
        }
        return product.updateStock(newQuantity);
    }

    public boolean hasEnoughStock(Product product, int quantityNeeded) {
        return product.hasEnoughStock(quantityNeeded);
    }
}
