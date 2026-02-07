package com.salesstore.product_service.service;

import com.salesstore.product_service.dto.request.CreateProductRequest;
import com.salesstore.product_service.dto.request.ProductSearchCriteria;
import com.salesstore.product_service.dto.response.PagedResponse;
import com.salesstore.product_service.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images);

    ProductResponse getProductById(@Valid UUID productId);

    void deleteProduct(UUID productId);

    PagedResponse<ProductResponse> searchProducts(ProductSearchCriteria criteria);
}
