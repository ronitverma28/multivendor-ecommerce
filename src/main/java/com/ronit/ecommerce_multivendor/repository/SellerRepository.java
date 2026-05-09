package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}
