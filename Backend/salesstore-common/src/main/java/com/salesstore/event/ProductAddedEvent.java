package com.salesstore.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductAddedEvent {
    private UUID productId;
    private String productName;
    private String description;
    private BigDecimal price;
}