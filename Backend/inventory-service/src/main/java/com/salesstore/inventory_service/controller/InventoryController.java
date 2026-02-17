package com.salesstore.inventory_service.controller;

import com.salesstore.inventory_service.dto.request.InventoryRequest;
import com.salesstore.inventory_service.dto.response.InventoryResponse;
import com.salesstore.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Void> updateStock(@Valid @RequestBody InventoryRequest request) {
        inventoryService.updateStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getStockBySku(sku));
    }

    // This specific endpoint will be called by Order Service via Feign later
    @GetMapping("/check/{sku}")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable String sku,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.checkAvailability(sku, quantity));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Inventory Service is up and running!");
    }
}