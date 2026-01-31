package com.salesstore.product_service.service;

import com.salesstore.product_service.dto.request.VariantRequest;
import com.salesstore.product_service.dto.response.VariantResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface VariantService {
    VariantResponse createVariant(@Valid VariantRequest variantRequest);

    List<VariantResponse> getVariantsByProductId(@Valid UUID productId);
}
