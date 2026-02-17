package com.salesstore.order_service.dto.request;

import com.salesstore.order_service.dto.request.OrderItemRequest;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String email;
    private String shippingAddress;
    private List<OrderItemRequest> items;

}
