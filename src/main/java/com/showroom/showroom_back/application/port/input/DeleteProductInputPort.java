package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

public interface DeleteProductInputPort {
    void deleteProduct(ProductId id);
}
