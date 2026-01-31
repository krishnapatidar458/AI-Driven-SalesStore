package com.salesstore.product_service.dto;

import lombok.Data;

@Data
public class VariantDTO {
    private Long VariantId;
    private String sku;
    private Integer stockQuantity;
    private String size;
    private String color;
}
