package com.salesstore.product_service.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductImageRequest {
   private String imageUrl;

   private String altText;

   private boolean isPrimary = false;

   private Integer displayOrder;
}
