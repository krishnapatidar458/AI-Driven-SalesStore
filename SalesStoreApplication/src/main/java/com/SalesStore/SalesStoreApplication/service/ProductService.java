package com.SalesStore.SalesStoreApplication.service;

import com.SalesStore.SalesStoreApplication.request.ProductRequest;
import com.SalesStore.SalesStoreApplication.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    Page<ProductResponse> search(
            String category,
            String brand,
            String keyword,
            Double minPrice,
            int page,
            int size
    );
}