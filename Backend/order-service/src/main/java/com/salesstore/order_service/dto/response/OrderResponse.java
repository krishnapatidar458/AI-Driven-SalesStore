package com.salesstore.order_service.dto.response;

import com.salesstore.order_service.model.OrderStatus;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        BigDecimal total,
        OrderStatus status
) {}