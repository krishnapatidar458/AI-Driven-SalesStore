package com.salesstore.product_service.repository;

import com.salesstore.product_service.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    List<Variant> findByProduct_ProductId(UUID productId);
}
