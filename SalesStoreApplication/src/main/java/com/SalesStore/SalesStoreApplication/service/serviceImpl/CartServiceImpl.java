package com.SalesStore.SalesStoreApplication.service.serviceImpl;

import com.SalesStore.SalesStoreApplication.entity.CartItem;
import com.SalesStore.SalesStoreApplication.repository.CartRepository;
import com.SalesStore.SalesStoreApplication.request.CartRequest;
import com.SalesStore.SalesStoreApplication.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void addToCart(String userEmail, CartRequest request) {
        CartItem item = CartItem.builder()
                .userEmail(userEmail)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
        cartRepository.save(item);
    }

    @Override
    public List<CartItem> getCart(String userEmail) {
        return cartRepository.findByUserEmail(userEmail);
    }
}
