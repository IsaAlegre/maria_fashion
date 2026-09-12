package com.showroom.showroom_back.domain.shared.valueobjects;

import java.util.Objects;

public record Stock(Integer quantity) {

    public Stock {
        Objects.requireNonNull(quantity, "Stock quantity cannot be null");
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    public static Stock of(Integer quantity) {
        return new Stock(quantity);
    }

    public static Stock empty() {
        return new Stock(0);
    }

    public boolean isSufficient(int needed) {
        if (needed < 0) {
            throw new IllegalArgumentException("Quantity needed cannot be negative");
        }
        return this.quantity >= needed;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }

    public boolean isInStock() {
        return this.quantity > 0;
    }

    public Stock decrease(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to decrease cannot be negative");
        }
        if (amount > this.quantity) {
            throw new IllegalArgumentException(
                    "Insufficient stock: trying to decrease by " + amount +
                            " but only " + this.quantity + " available"
            );
        }
        return new Stock(this.quantity - amount);
    }

    public Stock increase(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase cannot be negative");
        }
        return new Stock(this.quantity + amount);
    }

    public Stock set(int newQuantity) {
        return new Stock(newQuantity);
    }

    @Override
    public String toString() {
        return "Stock(" + quantity + ")";
    }
}