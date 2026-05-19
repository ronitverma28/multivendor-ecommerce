package com.ronit.ecommerce_multivendor.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronit.ecommerce_multivendor.dto.request.CouponRequest;
import com.ronit.ecommerce_multivendor.dto.response.CouponResponse;
import com.ronit.ecommerce_multivendor.service.CouponService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CouponResponse>> createNewCoupon(@Valid @RequestBody CouponRequest couponRequest){
        return ResponseEntity.ok(ApiResponse.ok("Coupon created successfully", couponService.createNewCoupon(couponRequest)));
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<Void>> applyCoupon(Authentication authentication,@NotBlank(message = "Coupon code cannot be empty") @RequestBody String couponCode){
        couponService.applyCoupon(authentication.getName(), couponCode);
        return ResponseEntity.ok(ApiResponse.ok("Coupon applied successfully", null));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<ApiResponse<List<CouponResponse>>> getAllCoupons(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Coupons fetched", couponService.getAllCoupons(pageable)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CouponResponse>> updateCouponStatus(@PathVariable Long id, @NotNull(message = "Active status cannot be null") @RequestBody Boolean isActive){
        return ResponseEntity.ok(ApiResponse.ok("Coupon status updated", couponService.updateCouponStatus(id, isActive)));
    }
}
