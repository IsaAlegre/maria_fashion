package com.showroom.showroom_back.domain.product;

public enum ProductStatus {
    DRAFT("Borrador"),
    ACTIVE("Activo"),
    INACTIVE("Inactivo"),
    DISCONTINUED("Descontinuado");

    private final String descripcion;

    ProductStatus(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
