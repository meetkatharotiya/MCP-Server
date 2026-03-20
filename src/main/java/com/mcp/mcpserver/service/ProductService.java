package com.mcp.mcpserver.service;

import com.mcp.mcpserver.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Product addProduct(Product product) {
        Long id = idGenerator.getAndIncrement();
        Product saved = new Product(id, product.name(), product.description(), product.price(), product.stockQuantity());
        products.put(id, saved);
        log.info("Product added: {}", saved);
        return saved;
    }

    public Collection<Product> listAll() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Collection<Product> searchByName(String query) {
        return products.values().stream()
                .filter(p -> p.name().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }

    public void clearAll() {
        products.clear();
        idGenerator.set(1);
    }
}