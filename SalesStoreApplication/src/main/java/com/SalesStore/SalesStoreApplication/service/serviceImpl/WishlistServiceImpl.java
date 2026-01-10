package com.SalesStore.SalesStoreApplication.service.serviceImpl;

import com.SalesStore.SalesStoreApplication.entity.WishlistItem;
import com.SalesStore.SalesStoreApplication.repository.WishlistRepository;
import com.SalesStore.SalesStoreApplication.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repository;

    @Override
    public void addToWishList(String userEmail, Long productId) {
        repository.save(
                WishlistItem.builder()
                        .userEmail(userEmail)
                        .productId(productId)
                        .build()
        );
    }

    @Override
    public List<WishlistItem> getWishList(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }
}
