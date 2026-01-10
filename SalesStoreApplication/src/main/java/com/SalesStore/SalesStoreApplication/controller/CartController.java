package com.SalesStore.SalesStoreApplication.controller;

import com.SalesStore.SalesStoreApplication.entity.CartItem;
import com.SalesStore.SalesStoreApplication.request.CartRequest;
import com.SalesStore.SalesStoreApplication.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController{

    private final CartService cartService;

    @PostMapping
    public void add(
            @RequestHeader("X-User-Email") String email,
            @RequestBody CartRequest request) {
        cartService.addToCart(email, request);
    }

    @GetMapping
    public List<CartItem> get(@RequestHeader("X-User-Email") String email) {
        return cartService.getCart(email);
    }
}