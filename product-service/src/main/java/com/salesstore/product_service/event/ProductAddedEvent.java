package com.salesstore.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data @AllArgsConstructor
public class ProductAddedEvent {
    private UUID productId;
    private String productName;
    private String description;
    private BigDecimal price;
}
