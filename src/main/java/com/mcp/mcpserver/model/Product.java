package com.mcp.mcpserver.model;

import java.math.BigDecimal;

/**
 * Represents a product in our local warehouse.
 * Using a record ensures immutability and reduces boilerplate.
 */
public record Product(
    Long id,
    String name,
    String description,
    BigDecimal price,
    int stockQuantity
) {}
