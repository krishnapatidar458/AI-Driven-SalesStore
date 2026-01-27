package com.salesstore.product_service.response;

import jdk.jshell.Snippet;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public class ProductResponse {
    private UUID productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private CategoryResponse category;
    private List<VariantResponse> variants;
    private boolean active;
    private LocalDateTime createdAt;

}
