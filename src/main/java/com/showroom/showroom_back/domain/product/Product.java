package com.showroom.showroom_back.domain.product;

import com.showroom.showroom_back.domain.shared.valueobjects.ProductDescription;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductName;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductPrice;
import com.showroom.showroom_back.domain.shared.valueobjects.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record Product(
        ProductId id,
        ProductName name,
        ProductDescription description,
        ProductPrice price,
        Stock stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Product {
        Objects.requireNonNull(id, "Product id cannot be null");
        Objects.requireNonNull(name, "Product name cannot be null");
        Objects.requireNonNull(description, "Product description cannot be null");
        Objects.requireNonNull(price, "Product price cannot be null");
        Objects.requireNonNull(stock, "Product stock cannot be null");
        Objects.requireNonNull(createdAt, "Product createdAt cannot be null");
        Objects.requireNonNull(updatedAt, "Product updatedAt cannot be null");
    }


    public static Product create(
            String name,
            String description,
            BigDecimal price,
            Integer stockQuantity) {

        LocalDateTime now = LocalDateTime.now();
        return new Product(
                ProductId.generate(),
                ProductName.of(name),
                ProductDescription.of(description),
                ProductPrice.of(price),
                Stock.of(stockQuantity),
                now,
                now
        );
    }


    public boolean isInStock() {
        return stock.isInStock();
    }

    public boolean hasEnoughStock(int quantityNeeded) {
        return stock.isSufficient(quantityNeeded);
    }

    public boolean isOutOfStock() {
        return stock.isEmpty();
    }


    public Product updateName(String newName) {
        ProductName updatedName = ProductName.of(newName);
        return new Product(
                this.id,
                updatedName,
                this.description,
                this.price,
                this.stock,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product updateDescription(String newDescription) {
        ProductDescription updatedDescription = ProductDescription.of(newDescription);
        return new Product(
                this.id,
                this.name,
                updatedDescription,
                this.price,
                this.stock,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product updatePrice(BigDecimal newPrice) {
        ProductPrice updatedPrice = ProductPrice.of(newPrice);
        return new Product(
                this.id,
                this.name,
                this.description,
                updatedPrice,
                this.stock,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product updateStock(int newQuantity) {
        Stock updatedStock = Stock.of(newQuantity);
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                updatedStock,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    // Métodos para operaciones de stock
    public Product decreaseStock(int quantity) {
        Stock updatedStock = this.stock.decrease(quantity);
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                updatedStock,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product increaseStock(int quantity) {
        Stock updatedStock = this.stock.increase(quantity);
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                updatedStock,
                this.createdAt,
                LocalDateTime.now()
        );
    }


    public ProductPrice totalPrice(int quantity) {
        return this.price.multiply(quantity);
    }
}