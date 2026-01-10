package com.SalesStore.SalesStoreApplication.specification;

import com.SalesStore.SalesStoreApplication.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, cb) ->
                brand == null ? null : cb.equal(root.get("brand"), brand);
    }

    public static Specification<Product> priceGreaterThan(Double price) {
        return (root, query, cb) ->
                price == null ? null : cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> nameContains(String keyword) {
        return (root, query, cb) ->
                keyword == null ? null :
                        cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}
