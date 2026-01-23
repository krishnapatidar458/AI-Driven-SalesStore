package com.salesstore.product_service.request;

import com.salesstore.product_service.model.Product;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VariantRequest {
    private String variantName;
    private String sku;
    private String size;
    private String color;
    private Integer stockQuantity;
    private Product product;
}
