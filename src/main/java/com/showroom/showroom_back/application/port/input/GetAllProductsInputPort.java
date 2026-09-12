package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.application.port.input.dto.ProductDto;

import java.util.List;

public interface GetAllProductsInputPort {
    List<ProductDto> getAllProducts();
}
