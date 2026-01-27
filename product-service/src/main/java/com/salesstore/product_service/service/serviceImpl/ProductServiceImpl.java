package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.event.ProductAddedEvent;
import com.salesstore.product_service.exception.ResourceNotFoundException.ResourceNotFoundException;
import com.salesstore.product_service.model.Category;
import com.salesstore.product_service.model.Product;
import com.salesstore.product_service.model.ProductImage;
import com.salesstore.product_service.repository.CategoryRepository;
import com.salesstore.product_service.repository.ProductImageRepository;
import com.salesstore.product_service.repository.ProductRepository;
import com.salesstore.product_service.request.CreateProductRequest;
import com.salesstore.product_service.response.ProductResponse;
import com.salesstore.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final FileStorageServiceImpl fileStorageService;
    private final KafkaTemplate<String,Object>  kafkaTemplate;

    @Override
    @Transactional
    public UUID createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images) {
        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow( () -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .category(category)
                .active(true)
                .build();

        Product savedProduct = productRepository.save(product);

        if(images != null && !images.isEmpty()){
            images.forEach( file -> {
                String fileName = fileStorageService.uploadFile(file);
                ProductImage img = new ProductImage();
                img.setProduct(savedProduct);
                img.setImageUrl(fileName);
                img.setPrimary(false);
                productImageRepository.save(img);
            });
        }

        // Publish event to Kafka
        kafkaTemplate.send("product-added-topic", new ProductAddedEvent(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getDescription(),
                savedProduct.getPrice()
        ));

        return savedProduct.getProductId();
    }
}
