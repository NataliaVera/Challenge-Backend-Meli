package com.challengemeli.config;

import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.usecase.product.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.challengemeli.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        @Bean
        public ProductUseCase productUseCase(ProductGateway productGateway) {
                return new ProductUseCase(productGateway);
        }
}
