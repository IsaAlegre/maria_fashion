package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.application.port.input.dto.CreateProductCommand;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

public interface CreateProductInputPort {
    ProductId execute(CreateProductCommand command);
}
