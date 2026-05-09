package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.dto.response.ProductResponse;
import com.ronit.ecommerce_multivendor.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT p FROM Product p
            WHERE
                (:title is NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:description is NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
            AND (:color is NULL OR LOWER(p.color) LIKE LOWER(CONCAT('%', :color, '%')))
            AND (:sizes is NULL OR LOWER(p.sizes) LIKE LOWER(CONCAT('%', :sizes, '%')))
            AND (:minPrice is NULL OR p.sellingPrice <= minPrice)
            AND (:maxPrice is NULL OR p.sellingPrice >= maxPrice)
            """)
    Page<ProductResponse> searchProducts(@Param("title") String title, @Param("description") String description, @Param("color") String color,@Param("sizes") String sizes,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice);

}
