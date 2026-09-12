package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.CreateProductInputPort;
import com.showroom.showroom_back.application.port.input.dto.CreateProductCommand;
import com.showroom.showroom_back.application.port.output.ProductRepositoryPort;
import com.showroom.showroom_back.domain.product.Product;
import com.showroom.showroom_back.domain.product.ProductDomainService;
import com.showroom.showroom_back.domain.shared.valueobjects.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase implements CreateProductInputPort {

    private final ProductRepositoryPort productRepository;
    private final ProductDomainService productService;


    @Override
    public ProductId execute(CreateProductCommand command){
        Product product = Product.create(
                command.name(),
                command.description(),
                command.price(),
                command.stockQuantity()
        );

        Product saved = productRepository.save(product);
        return saved.id();
    }
}
