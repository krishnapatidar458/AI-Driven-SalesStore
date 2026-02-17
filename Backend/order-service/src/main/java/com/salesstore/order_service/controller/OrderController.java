package com.salesstore.order_service.controller;

import com.salesstore.order_service.dto.request.OrderRequest;
import com.salesstore.order_service.dto.response.OrderResponse;
import com.salesstore.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        // For the Skeleton, we hardcode the userSub.
        // Later, we will extract this from the Principal/SecurityContext.
        String tempUserSub = "user-123";
        return ResponseEntity.ok(orderService.placeOrder(request, tempUserSub));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Order Service is up and running!");
    }
}