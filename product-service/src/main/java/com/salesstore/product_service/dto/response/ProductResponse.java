package com.salesstore.product_service.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse (
    UUID productId,
    String productName,
    String description,
    BigDecimal price,
    String categoryName,
    List<VariantResponse> variants,
    List<String> imageUrls
) {}
