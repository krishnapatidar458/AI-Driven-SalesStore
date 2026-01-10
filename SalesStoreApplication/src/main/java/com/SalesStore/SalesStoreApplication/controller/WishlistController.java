package com.SalesStore.SalesStoreApplication.controller;

import com.SalesStore.SalesStoreApplication.entity.WishlistItem;
import com.SalesStore.SalesStoreApplication.request.WishlistRequest;
import com.SalesStore.SalesStoreApplication.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @PostMapping
    public void addToWishList(
            @RequestHeader("X-User-Email") String email,
            @RequestBody WishlistRequest request) {
        service.addToWishList(email, request.getProductId());
    }

    @GetMapping
    public List<WishlistItem> getWishList(@RequestHeader("X-User-Email") String email) {
        return service.getWishList(email);
    }
}
