package com.salesstore.product_service.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchCriteria {
    private String Query;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean active = true;

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "DESC";
}
