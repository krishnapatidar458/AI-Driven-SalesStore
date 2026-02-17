package com.salesstore.inventory_service.dto.response;

public record InventoryResponse(
        String sku, Integer quantity, boolean isInStock
) {}