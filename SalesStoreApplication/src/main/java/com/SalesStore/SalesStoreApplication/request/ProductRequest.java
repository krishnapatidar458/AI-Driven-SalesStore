package com.SalesStore.SalesStoreApplication.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class ProductRequest{
    private String name;
    private String category;
    private String brand;
    private double price;
    private double rating;
}