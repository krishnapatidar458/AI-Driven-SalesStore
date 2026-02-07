package com.salesstore.product_service.repository.specification;

import com.salesstore.product_service.dto.request.ProductSearchCriteria;
import com.salesstore.product_service.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

    public static Specification<Product> getSpecification(ProductSearchCriteria productSearchCriteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.hasText(productSearchCriteria.getQuery())){
                String pattern = "%" + productSearchCriteria.getQuery().toLowerCase() + "%";
                Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), pattern);
                Predicate descriptionMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern);
                predicates.add(criteriaBuilder.or(nameMatch, descriptionMatch));
            }

            if(productSearchCriteria.getMinPrice() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productSearchCriteria.getMinPrice()));
            }

            if(productSearchCriteria.getMaxPrice() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), productSearchCriteria.getMaxPrice()));
            }

            if(productSearchCriteria.getCategoryId() != null){
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), productSearchCriteria.getCategoryId()));
            }

            if(productSearchCriteria.getActive() != null){
                predicates.add(criteriaBuilder.equal(root.get("active"), productSearchCriteria.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
