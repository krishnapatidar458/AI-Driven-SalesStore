package com.salesstore.product_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
}
