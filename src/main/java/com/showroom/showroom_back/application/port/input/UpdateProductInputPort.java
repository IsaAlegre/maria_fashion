package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.application.port.input.dto.UpdateProductCommand;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

public interface UpdateProductInputPort {
    void updateProduct(ProductId id, UpdateProductCommand command);
}
