package com.salesstore.product_service.controller;

import com.salesstore.product_service.dto.request.VariantRequest;
import com.salesstore.product_service.dto.response.VariantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.salesstore.product_service.service.VariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @PostMapping
    public ResponseEntity<VariantResponse> createVariant(@Valid @RequestBody VariantRequest variantRequest) {
        VariantResponse variantResponse = variantService.createVariant(variantRequest);
        return new ResponseEntity<>(variantResponse, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariantResponse>> getVariantsByProductId(@Valid @PathVariable UUID productId) {
        return ResponseEntity.ok(variantService.getVariantsByProductId(productId));
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Variant Service is up and running!";
    }

}
