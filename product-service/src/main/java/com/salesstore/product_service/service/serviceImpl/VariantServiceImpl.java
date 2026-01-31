package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.exception.ResourceNotFoundException;
import com.salesstore.product_service.model.Product;
import com.salesstore.product_service.model.Variant;
import com.salesstore.product_service.repository.ProductRepository;
import com.salesstore.product_service.repository.VariantRepository;
import com.salesstore.product_service.dto.request.VariantRequest;
import com.salesstore.product_service.dto.response.VariantResponse;
import com.salesstore.product_service.service.VariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public VariantResponse createVariant(VariantRequest variantRequest) {
        Product product = productRepository.findById(variantRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Variant variant = new Variant();
        variant.setProduct(product);
        variant.setSku(variantRequest.getSku());
        variant.setSize(variantRequest.getSize());
        variant.setColor(variantRequest.getColor());
        variant.setStockQuantity(variantRequest.getStockQuantity());

        Variant savedVariant = variantRepository.save(variant);
        return mapToVariantResponse(savedVariant);
    }

    @Override
    @Cacheable(value = "variants", key = "#productId")
    public List<VariantResponse> getVariantsByProductId(UUID productId) {
        return variantRepository.findByProduct_ProductId(productId).stream()
                .map(this::mapToVariantResponse)
                .collect(Collectors.toList());
    }

    private VariantResponse mapToVariantResponse(Variant variant) {
        return new VariantResponse(
                variant.getVariantId(),
                variant.getSku(),
                variant.getStockQuantity(),
                variant.getSize(),
                variant.getColor(),
                variant.getProduct().getProductId()
        );
    }
}
