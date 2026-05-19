package com.ronit.ecommerce_multivendor.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ronit.ecommerce_multivendor.dto.request.CouponRequest;
import com.ronit.ecommerce_multivendor.dto.response.CouponResponse;
import com.ronit.ecommerce_multivendor.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService{

    @Override
    public CouponResponse createNewCoupon(CouponRequest couponRequest) {
        return null;
    }

    @Override
    public List<CouponResponse> getAllCoupons(Pageable pageable) {
        return null;
    }

    @Override
    public void applyCoupon(String email, String couponCode) {
      
    }

    @Override
    public CouponResponse updateCouponStatus(Long id, Boolean isActive) {
        return null;
    }
    
}
