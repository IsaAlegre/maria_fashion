package com.showroom.showroom_back.domain.product;

import com.showroom.showroom_back.domain.common.exception.InsufficientStockException;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductPrice;

import java.util.Objects;


public class ProductDomainService {

    /**
     * Valida que un producto tenga stock suficiente
     */
    public void validateSufficientStock(Product product, int quantityNeeded) {
        if (!product.hasEnoughStock(quantityNeeded)) {
            throw new InsufficientStockException(
                    "Product '" + product.name().value() + "' has only " +
                            product.stock().quantity() + " units, but " + quantityNeeded +
                            " were requested"
            );
        }
    }

    /**
     * Calcula el total de una compra
     */
    public ProductPrice calculateTotal(Product product, int quantity) {
        validateSufficientStock(product, quantity);
        return product.totalPrice(quantity);
    }

    /**
     * Valida que un producto se pueda actualizar
     */
    public void validateProductCanBeUpdated(Product product) {
        // Aquí podrías agregar lógica: ej, no actualizar si está en una orden
        Objects.requireNonNull(product, "Product cannot be null");
    }

    /**
     * Valida que un producto se pueda eliminar
     */
    public void validateProductCanBeDeleted(Product product) {
        // Aquí podrías agregar lógica: ej, no eliminar si tiene órdenes pendientes
        Objects.requireNonNull(product, "Product cannot be null");
    }
}
