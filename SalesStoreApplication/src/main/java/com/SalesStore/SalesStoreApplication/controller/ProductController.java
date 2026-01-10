package com.SalesStore.SalesStoreApplication.controller;

import com.SalesStore.SalesStoreApplication.exception.AccessDeniedException;
import com.SalesStore.SalesStoreApplication.request.ProductRequest;
import com.SalesStore.SalesStoreApplication.response.ProductResponse;
import com.SalesStore.SalesStoreApplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin")
    public ProductResponse createAdmin(
            @RequestHeader("X-User-Role") String role,
            @RequestBody ProductRequest request) {

        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Admin access required");
        }
        return productService.create(request);
    }


    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @GetMapping
    public Page<ProductResponse> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.search(category, brand, keyword, minPrice, page, size);
    }
}