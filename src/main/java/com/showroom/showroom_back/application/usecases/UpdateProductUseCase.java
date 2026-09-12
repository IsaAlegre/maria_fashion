package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.UpdateProductInputPort;
import com.showroom.showroom_back.application.port.input.dto.UpdateProductCommand;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.product.ProductDomainService;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase implements UpdateProductInputPort {

    private final ProductDomainService productDomainService;
    private final ProductRepositoryPort productRepository;

    @Override
    public void updateProduct(ProductId id, UpdateProductCommand command){

        Product product = productRepository.findById(id);
        Product updated = product
                .updateName(command.name())
                .updateDescription(command.description())
                .updatePrice(command.price())
                .updateStock(command.stockQuantity());

        productRepository.save(updated);

    }
}
