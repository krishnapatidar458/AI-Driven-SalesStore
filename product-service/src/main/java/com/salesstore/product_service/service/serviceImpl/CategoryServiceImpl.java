package com.salesstore.product_service.service.serviceImpl;

import com.salesstore.product_service.exception.ResourceNotFoundException;
import com.salesstore.product_service.model.Category;
import com.salesstore.product_service.repository.CategoryRepository;
import com.salesstore.product_service.dto.request.CategoryRequest;
import com.salesstore.product_service.dto.response.CategoryResponse;
import com.salesstore.product_service.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final String CATEGORY_CACHE = "categories";


    @Override
    @Transactional
    @CacheEvict(value = "CATEGORY_CACHE", allEntries = true)
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getName());
        category.setSlug(categoryRequest.getName().toLowerCase().replace(" ","-"));
        if(categoryRequest.getParentId() != null){
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);

    }

    private CategoryResponse mapToCategoryResponse(Category savedCategory) {
        List<CategoryResponse> subCategories = (savedCategory.getSubCategories() == null)
                ? Collections.emptyList() : savedCategory.getSubCategories()
                .stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
        return new CategoryResponse(
                savedCategory.getCategoryId(),
                savedCategory.getCategoryName(),
                savedCategory.getSlug(),
                (savedCategory.getParentCategory() != null) ? savedCategory.getParentCategory().getCategoryId() : null,
                subCategories
        );
    }

    @Override
    @Cacheable(value = "CATEGORY_CACHE", key = "'allCategories'")
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "CATEGORY_CACHE", key = "#categoryId")
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapToCategoryResponse(category);
    }
}
