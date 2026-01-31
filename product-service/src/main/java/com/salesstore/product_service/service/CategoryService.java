package com.salesstore.product_service.service;

import com.salesstore.product_service.dto.request.CategoryRequest;
import com.salesstore.product_service.dto.response.CategoryResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(@Valid CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(@Valid Long categoryId);
}
