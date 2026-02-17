package com.salesstore.order_service.service.Impl;

import com.salesstore.event.OrderItemEvent;
import com.salesstore.event.OrderPlacedEvent;
import com.salesstore.order_service.client.InventoryClient;
import com.salesstore.order_service.dto.request.OrderRequest;
import com.salesstore.order_service.dto.response.OrderResponse;
import com.salesstore.order_service.exception.OutOfStockException;
import com.salesstore.order_service.model.Order;
import com.salesstore.order_service.model.OrderItem;
import com.salesstore.order_service.model.OrderStatus;
import com.salesstore.order_service.repository.OrderRepository;
import com.salesstore.order_service.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackPlaceOrder")
    @Retry(name = "inventory")
    public OrderResponse placeOrder(OrderRequest request, String userSub) {
        log.info("Processing order request for user: {}", userSub);

        // 1. THE GUARD: Sync check via Feign
        for (var itemRequest : request.getItems()) {
            boolean inStock = inventoryClient.checkAvailability(
                    itemRequest.getSku(),
                    itemRequest.getQuantity()
            );

            if (!inStock) {
                throw new OutOfStockException("Item " + itemRequest.getProductName() +
                        " (SKU: " + itemRequest.getSku() + ") is out of stock!");
            }
        }

        // 2. Persist to Database
        Order order = mapToOrder(request, userSub);
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved with ID: {}", savedOrder.getId());

        // 3. ASYNC: Emit Kafka event for Inventory reduction
        kafkaTemplate.send("order-placed-topic", mapToEvent(savedOrder, request.getEmail()));
        log.info("OrderPlacedEvent emitted to Kafka for order: {}", savedOrder.getId());

        return new OrderResponse(savedOrder.getId(), savedOrder.getTotalAmount(), savedOrder.getStatus());
    }

    /**
     * FALLBACK: Triggered when Inventory Service is down or Circuit is OPEN.
     */
    public OrderResponse fallbackPlaceOrder(OrderRequest request, String userSub, Throwable throwable) {
        log.error("Circuit Breaker triggered! Reason: {}", throwable.getMessage());
        throw new RuntimeException("Inventory Service is currently unavailable. Please try again later.");
    }

    /**
     * MAPPING: Converts Request DTO to Order Entity and calculates total.
     */
    private Order mapToOrder(OrderRequest request, String userSub) {
        Order order = new Order();
        order.setUserSub(userSub);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = request.getItems().stream().map(itemRequest ->
                OrderItem.builder()
                        .order(order) // Sets the back-reference
                        .productId(itemRequest.getProductId())
                        .productName(itemRequest.getProductName())
                        .priceAtPurchase(itemRequest.getPrice())
                        .quantity(itemRequest.getQuantity())
                        .sku(itemRequest.getSku())
                        .build()
        ).collect(Collectors.toList());

        order.setOrderItems(items);

        // Precise BigDecimal calculation for the total
        BigDecimal total = items.stream()
                .map(i -> i.getPriceAtPurchase().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
        return order;
    }

    /**
     * MAPPING: Converts saved Entity to Kafka Event DTO.
     */
    private OrderPlacedEvent mapToEvent(Order savedOrder, String email) {
        List<OrderItemEvent> itemEvents = savedOrder.getOrderItems().stream()
                .map(i -> new OrderItemEvent(i.getSku(), i.getQuantity()))
                .collect(Collectors.toList());

        return new OrderPlacedEvent(savedOrder.getId().toString(),email, itemEvents);
    }
}