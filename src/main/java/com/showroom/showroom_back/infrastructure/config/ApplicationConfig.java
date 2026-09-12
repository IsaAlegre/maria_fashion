package com.showroom.showroom_back.infrastructure.config;

import com.showroom.showroom_back.domain.product.ProductDomainService;
import com.showroom.showroom_back.domain.stock.StockDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ProductDomainService productDomainService(){
        return new ProductDomainService();
    }

    @Bean
    public StockDomainService stockDomainService() {
        return new StockDomainService();
    }

}
