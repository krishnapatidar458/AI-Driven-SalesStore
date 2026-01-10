package com.SalesStore.SalesStoreApplication.repository;

import com.SalesStore.SalesStoreApplication.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserEmail(String userEmail);
}
