package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Cart;
import com.ronit.ecommerce_multivendor.model.CartItem;
import com.ronit.ecommerce_multivendor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
