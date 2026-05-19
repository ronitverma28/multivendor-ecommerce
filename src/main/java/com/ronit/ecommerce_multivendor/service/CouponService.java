package com.ronit.ecommerce_multivendor.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.ronit.ecommerce_multivendor.dto.request.CouponRequest;
import com.ronit.ecommerce_multivendor.dto.response.CouponResponse;

public interface CouponService {
    CouponResponse createNewCoupon(CouponRequest couponRequest);
    List<CouponResponse> getAllCoupons(Pageable pageable);
    void applyCoupon(String email, String couponCode);
    CouponResponse updateCouponStatus(Long id, Boolean isActive);
}
