package com.SalesStore.SalesStoreApplication.service;

import com.SalesStore.SalesStoreApplication.entity.WishlistItem;

import java.util.List;

public interface WishlistService {
    void addToWishList(String userEmail, Long productId);
    List<WishlistItem> getWishList(String userEmail);
}