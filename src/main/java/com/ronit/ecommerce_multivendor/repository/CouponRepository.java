package com.ronit.ecommerce_multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ronit.ecommerce_multivendor.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{
 
} 
