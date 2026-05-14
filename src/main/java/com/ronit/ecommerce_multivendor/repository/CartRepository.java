package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUser_Id(Long id);
    Optional<Cart> findByUser_Id(Long id);
}
