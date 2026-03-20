package com.mcp.mcpserver.mcp.tool;

import com.mcp.mcpserver.mcp.annotation.McpTool;
import com.mcp.mcpserver.model.Product;
import com.mcp.mcpserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
@McpTool
@RequiredArgsConstructor
public class ProductTools {

    private final ProductService productService;

    @Tool(description = "Add a new product to the warehouse. Needs name, description, price, and initial stock quantity.")
    public Product addProduct(String name, String description, double price, int stock) {
        validateAddProduct(name, description, price, stock);
        log.info("Tool invoked: addProduct(name={})", name);
        return productService.addProduct(new Product(null, name, description, BigDecimal.valueOf(price), stock));
    }

    @Tool(description = "List all products in the warehouse.")
    public Collection<Product> listProducts() {
        log.info("Tool invoked: listProducts");
        return productService.listAll();
    }

    @Tool(description = "Find products by name or part of a name (case-insensitive).")
    public Collection<Product> searchProduct(String query) {
        validateNotBlank(query, "Search query");
        log.info("Tool invoked: searchProduct(query={})", query);
        return productService.searchByName(query);
    }

    @Tool(description = "Remove a product by its unique ID. Returns confirmation or not-found message.")
    public String removeProduct(Long id) {
        validateId(id);
        log.info("Tool invoked: removeProduct(id={})", id);
        return productService.deleteProduct(id)
                ? "Successfully removed product " + id
                : "Product ID " + id + " not found.";
    }

    private void validateAddProduct(String name, String description, double price, int stock) {
        validateNotBlank(name, "Product name");
        validateNotBlank(description, "Product description");
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than 0");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Invalid product ID: " + id);
    }

    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(fieldName + " must not be blank");
    }
}