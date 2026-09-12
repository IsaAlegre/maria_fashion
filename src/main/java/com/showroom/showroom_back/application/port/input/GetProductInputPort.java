package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.application.port.input.dto.ProductDto;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;

public interface GetProductInputPort {
    ProductDto getProduct(ProductId productId);
}
