package com.showroom.showroom_back.domain.common.exception;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String message) {
        super(message);
    }
}