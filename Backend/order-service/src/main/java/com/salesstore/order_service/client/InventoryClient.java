package com.salesstore.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// name must match the spring.application.name of the inventory-service
@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/v1/inventory/check/{sku}")
    boolean checkAvailability(
            @PathVariable("sku") String sku,
            @RequestParam("quantity") Integer quantity
    );
}