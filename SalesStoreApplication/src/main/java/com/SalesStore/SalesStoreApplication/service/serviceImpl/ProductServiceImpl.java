package com.SalesStore.SalesStoreApplication.service.serviceImpl;

import com.SalesStore.SalesStoreApplication.entity.Product;
import com.SalesStore.SalesStoreApplication.repository.ProductRepository;
import com.SalesStore.SalesStoreApplication.request.ProductRequest;
import com.SalesStore.SalesStoreApplication.response.ProductResponse;
import com.SalesStore.SalesStoreApplication.service.ProductService;
import com.SalesStore.SalesStoreApplication.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .brand(request.getBrand())
                .price(request.getPrice())
                .rating(request.getRating())
                .build();

        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getRating()
        );
    }

    @Override
    public Page<ProductResponse> search(
            String category,
            String brand,
            String keyword,
            Double minPrice,
            int page,
            int size) {

        Specification<Product> spec = (root, query, cb) -> null;

        if (category != null) {
            spec = spec.and(ProductSpecification.hasCategory(category));
        }
        if (brand != null) {
            spec = spec.and(ProductSpecification.hasBrand(brand));
        }
        if (keyword != null) {
            spec = spec.and(ProductSpecification.nameContains(keyword));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterThan(minPrice));
        }

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(spec, pageable)
                .map(p -> new ProductResponse(
                        p.getId(), p.getName(), p.getCategory(),
                        p.getBrand(), p.getPrice(), p.getRating()
                ));
    }
}
