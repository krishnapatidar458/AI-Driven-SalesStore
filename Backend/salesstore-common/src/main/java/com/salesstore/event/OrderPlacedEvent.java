package com.salesstore.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderPlacedEvent {
    private String orderId;
    private List<OrderItemEvent> items;
}