package com.mcp.mcpserver.config;

import com.mcp.mcpserver.model.Product;
import com.mcp.mcpserver.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Configuration class to seed the in-memory database on startup.
 * Using CommandLineRunner is a standard practice for dev-data setup.
 */
@Slf4j
@Configuration
public class DataInitializer {



    @Bean
    public CommandLineRunner loadData(ProductService productService) {
        return args -> {
            productService.addProduct(new Product(
                null, 
                "MacBook Pro 16", 
                "Apple M3 Max, 64GB RAM, 2TB SSD", 
                BigDecimal.valueOf(3499.99), 
                10
            ));

            productService.addProduct(new Product(
                null, 
                "Keychron Q1", 
                "Mechanical Keyboard with Gateron Brown Switches", 
                BigDecimal.valueOf(169.00), 
                25
            ));

            productService.addProduct(new Product(
                null, 
                "Dell UltraSharp 32", 
                "4K USB-C Hub Monitor", 
                BigDecimal.valueOf(899.50), 
                15
            ));
            
            log.info("Inventory seeded with {} products.", productService.listAll().size());
        };
    }
}
