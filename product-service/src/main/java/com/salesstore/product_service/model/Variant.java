package com.salesstore.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;

    @Column(unique = true, nullable = false)
    private String sku; // e.g., "NIKE-AIR-BLK-42"

    private String size;  // "XL"
    private String color; // "Black"
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore // Prevent infinite recursion in JSON
    private Product product;
}
