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
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Product {
        Objects.requireNonNull(id, "Product id cannot be null");
        Objects.requireNonNull(name, "Product name cannot be null");
        Objects.requireNonNull(description, "Product description cannot be null");
        Objects.requireNonNull(price, "Product price cannot be null");
        Objects.requireNonNull(stock, "Product stock cannot be null");
        Objects.requireNonNull(status, "Product status cannot be null");
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
                ProductStatus.DRAFT,
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
                this.status,
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
                this.status,
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
                this.status,
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
                this.status,
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
                this.status,
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
                this.status,
                this.createdAt,
                LocalDateTime.now()
        );
    }


    public ProductPrice totalPrice(int quantity) {
        return this.price.multiply(quantity);
    }

    // Métodos para cambiar estado
    public Product publish() {
        if (this.status != ProductStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT products can be published");
        }
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                ProductStatus.ACTIVE,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product unpublish() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                ProductStatus.INACTIVE,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product discontinue() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                ProductStatus.DISCONTINUED,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public Product updateStatus(ProductStatus newStatus) {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                newStatus,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    // Query métodos para estado
    public boolean isActive() {
        return this.status == ProductStatus.ACTIVE;
    }

    public boolean canBePurchased() {
        return this.status == ProductStatus.ACTIVE && this.stock.isInStock();
    }

    public boolean isDraft() {
        return this.status == ProductStatus.DRAFT;
    }

    public boolean isDiscontinued() {
        return this.status == ProductStatus.DISCONTINUED;
    }
}