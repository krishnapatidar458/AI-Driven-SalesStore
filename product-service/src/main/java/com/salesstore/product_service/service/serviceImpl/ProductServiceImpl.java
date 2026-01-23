package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.model.Product;
import com.salesstore.product_service.repository.ProductRepository;
import com.salesstore.product_service.request.ProductRequest;
import com.salesstore.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .active(productRequest.isActive())
                .build();

        if(productRequest.variants() !=

        return productRepository.save(product);
    }
}
