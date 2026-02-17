package com.salesstore.product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class VariantRequest {
    @NotBlank private String sku;
    @Min(0) private String size;
    private String color;
    private Integer stockQuantity;
    private UUID productId;
}
