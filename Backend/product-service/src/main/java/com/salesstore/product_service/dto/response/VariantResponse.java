package com.salesstore.product_service.dto.response;

import java.util.UUID;

public record VariantResponse(
    Long id,
    String sku,
    Integer stockQuantity,
    String size,
    String color,
    UUID productId
) {}
