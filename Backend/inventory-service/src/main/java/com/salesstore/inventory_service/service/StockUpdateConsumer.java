package com.salesstore.inventory_service.service;

import com.salesstore.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.salesstore.inventory_service.model.Inventory;
import com.salesstore.inventory_service.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockUpdateConsumer {

    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-placed-topic", groupId = "inventory-group")
    @Transactional
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Inventory Service: Processing order {}", event.getOrderId());

        event.getItems().forEach(item -> {
            Inventory inventory = inventoryRepository.findBySku(item.getSku())
                    .orElseThrow(() -> new RuntimeException("SKU not found during async update: " + item.getSku()));

            // Subtract the stock
            int newQuantity = inventory.getQuantity() - item.getQuantity();
            inventory.setQuantity(newQuantity);

            inventoryRepository.save(inventory);
            log.info("Stock reduced for SKU: {}. Remaining: {}", item.getSku(), newQuantity);
        });
    }
}