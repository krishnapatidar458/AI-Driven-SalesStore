package com.SalesStore.SalesStoreApplication.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String category;
    private String brand;
    private double price;
    private double rating;
}

