package com.SalesStore.SalesStoreApplication.service;

import com.SalesStore.SalesStoreApplication.entity.CartItem;
import com.SalesStore.SalesStoreApplication.request.CartRequest;

import java.util.List;

public interface CartService {

    void addToCart(String userEmail, CartRequest request);
    List<CartItem> getCart(String userEmail);
}
