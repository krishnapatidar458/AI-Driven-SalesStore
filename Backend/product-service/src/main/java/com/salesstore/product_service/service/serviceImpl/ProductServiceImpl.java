package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.dto.request.ProductSearchCriteria;
import com.salesstore.product_service.dto.response.PagedResponse;
import com.salesstore.product_service.event.ProductAddedEvent;
import com.salesstore.product_service.exception.ResourceNotFoundException;
import com.salesstore.product_service.model.Category;
import com.salesstore.product_service.model.Product;
import com.salesstore.product_service.model.ProductImage;
import com.salesstore.product_service.repository.CategoryRepository;
import com.salesstore.product_service.repository.ProductImageRepository;
import com.salesstore.product_service.repository.ProductRepository;
import com.salesstore.product_service.dto.request.CreateProductRequest;
import com.salesstore.product_service.dto.response.ProductResponse;
import com.salesstore.product_service.dto.response.VariantResponse;
import com.salesstore.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @CacheEvict(value = { "products", "categories" }, allEntries = true)
    public ProductResponse createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images) {
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

        return mapToProductResponse(savedProduct);
    }



    @Override
    @Cacheable(value = "products", key = "#productId")
    public ProductResponse getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(this::mapToProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);

    }

    @Override
    public PagedResponse<ProductResponse> searchProducts(ProductSearchCriteria criteria) {
        Sort sort = criteria.getSortDir().equalsIgnoreCase("asc") ?
                Sort.by(criteria.getSortBy()).ascending() :
                Sort.by(criteria.getSortBy()).descending();

        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        Page<Product> productPage = productRepository.findAll(
                com.salesstore.product_service.repository.specification.ProductSpecification.getSpecification(criteria),
                pageable
        );

        List<ProductResponse> content = productPage.getContent().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        List<String> imageUels = savedProduct.getProductImages()==null
                ? Collections.emptyList()
                : savedProduct.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        List<VariantResponse> variantResponses = savedProduct.getVariants() == null
                ? Collections.emptyList()
                : savedProduct.getVariants().stream()
                .map(variant -> new VariantResponse(
                        variant.getVariantId(),
                        variant.getSku(),
                        variant.getStockQuantity(),
                        variant.getSize(),
                        variant.getColor(),
                        savedProduct.getProductId()
                ))
                .collect(Collectors.toList());

        return new ProductResponse(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getCategory().getCategoryName(),
                variantResponses,
                imageUels
        );

    }
}
