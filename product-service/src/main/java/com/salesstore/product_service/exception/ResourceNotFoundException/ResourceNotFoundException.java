package com.salesstore.product_service.exception.ResourceNotFoundException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
