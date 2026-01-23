package com.salesstore.product_service.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private boolean active;
    private List<VariantRequest> variants;
    private List<>
}
