package com.salesstore.product_service.controller;

import com.salesstore.product_service.dto.request.CreateProductRequest;
import com.salesstore.product_service.dto.request.ProductSearchCriteria;
import com.salesstore.product_service.dto.response.PagedResponse;
import com.salesstore.product_service.dto.response.ProductResponse;
import com.salesstore.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponse> createProduct(
         @Valid   @RequestPart("product") CreateProductRequest createProductRequest,
         @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        ProductResponse productResponse = productService.createProduct(createProductRequest, images);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    private ResponseEntity<ProductResponse> getProductById(@Valid @PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("{productId}")
    private ResponseEntity<Void> deleteProductById(@PathVariable UUID productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<ProductResponse>>  searchProducts(
            @ModelAttribute ProductSearchCriteria criteria) {
        PagedResponse<ProductResponse> response = productService.searchProducts(criteria);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/health")
    public String healthCheck() {
        return "Product Service is up and running!";
    }
}
