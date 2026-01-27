package com.salesstore.product_service.controller;

import com.salesstore.product_service.repository.ProductRepository;
import com.salesstore.product_service.request.CreateProductRequest;
import com.salesstore.product_service.response.ProductResponse;
import com.salesstore.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UUID> addProduct(
            @RequestPart("product") CreateProductRequest createProductRequest,
            @RequestPart("images") List<MultipartFile> images) {

        UUID productId = productService.createProduct(createProductRequest, images);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Product Service is up and running!";
    }
}
