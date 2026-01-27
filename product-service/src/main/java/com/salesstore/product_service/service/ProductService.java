package com.salesstore.product_service.service;

import com.salesstore.product_service.request.CreateProductRequest;
import com.salesstore.product_service.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    UUID createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images);
}
