package com.salesstore.inventory_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventoryRequest {
    @NotBlank private String sku;
    @Min(0) private Integer quantity;
}