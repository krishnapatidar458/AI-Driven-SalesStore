package com.salesstore.product_service.service;

import com.salesstore.product_service.model.Product;
import com.salesstore.product_service.request.ProductRequest;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
}
