package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
