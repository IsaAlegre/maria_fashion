package com.showroom.showroom_back.domain.stock;

import com.showroom.showroom_back.domain.product.Product;

// domain/stock/StockDomainService.java
public class StockDomainService {

    /**
     * Reserva stock para una orden
     */
    public Product reserveStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reserve must be positive");
        }
        return product.decreaseStock(quantity);
    }

    /**
     * Libera stock (deshace una reserva)
     */
    public Product releaseStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to release must be positive");
        }
        return product.increaseStock(quantity);
    }

    /**
     * Ajusta el stock a un valor específico
     */
    public Product adjustStock(Product product, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        return product.updateStock(newQuantity);
    }

    /**
     * Valida si hay stock suficiente sin modificar
     */
    public boolean hasEnoughStock(Product product, int quantityNeeded) {
        return product.hasEnoughStock(quantityNeeded);
    }
}
