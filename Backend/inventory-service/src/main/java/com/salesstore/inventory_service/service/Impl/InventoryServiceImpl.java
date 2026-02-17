package com.salesstore.inventory_service.service.Impl;

import com.salesstore.inventory_service.dto.request.InventoryRequest;
import com.salesstore.inventory_service.dto.response.InventoryResponse;
import com.salesstore.inventory_service.model.Inventory;
import com.salesstore.inventory_service.repository.InventoryRepository;
import com.salesstore.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public void updateStock(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findBySku(request.getSku())
                .orElse(Inventory.builder().sku(request.getSku()).quantity(0).build());

        inventory.setQuantity(request.getQuantity());
        inventoryRepository.save(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getStockBySku(String sku) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("SKU not found"));

        return new InventoryResponse(inventory.getSku(), inventory.getQuantity(), inventory.getQuantity() > 0);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkAvailability(String sku, Integer requestedQuantity) {
        return inventoryRepository.findBySku(sku)
                .map(inv -> inv.getQuantity() >= requestedQuantity)
                .orElse(false);
    }
}