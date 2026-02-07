package com.salesstore.product_service.dto.response;

import java.util.List;

public record CategoryResponse(
   Long id,
   String categoryName,
   String slug,
   Long parentId,
   List<CategoryResponse> subcategories
) {}
