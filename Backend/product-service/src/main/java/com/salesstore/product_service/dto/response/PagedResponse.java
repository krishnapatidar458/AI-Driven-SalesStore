package com.salesstore.product_service.dto.response;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean lastPage
) {}
