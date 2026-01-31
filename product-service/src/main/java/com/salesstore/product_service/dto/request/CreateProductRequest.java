package com.salesstore.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank
    private String productName;
    private String description;

    @NonNull
    @Positive
    private BigDecimal price;

    @NonNull
    private Long categoryId;

}
