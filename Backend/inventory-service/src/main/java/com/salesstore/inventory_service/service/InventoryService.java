package com.salesstore.inventory_service.service;

import com.salesstore.inventory_service.dto.request.InventoryRequest;
import com.salesstore.inventory_service.dto.response.InventoryResponse;

public interface InventoryService {
    void updateStock(InventoryRequest request);
    InventoryResponse getStockBySku(String sku);
    boolean checkAvailability(String sku, Integer requestedQuantity);
}