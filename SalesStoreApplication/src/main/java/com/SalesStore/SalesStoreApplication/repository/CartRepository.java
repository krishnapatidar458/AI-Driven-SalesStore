package com.SalesStore.SalesStoreApplication.repository;

import com.SalesStore.SalesStoreApplication.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByUserEmail(String userEmail);
}
