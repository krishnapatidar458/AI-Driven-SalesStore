package com.salesstore.order_service.service;

import com.salesstore.order_service.dto.request.OrderRequest;
import com.salesstore.order_service.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request, String tempUserSub);
}
