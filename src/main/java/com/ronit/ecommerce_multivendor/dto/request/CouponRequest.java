package com.ronit.ecommerce_multivendor.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponRequest {
    private String code;
    private Double discountPercentage;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private Double minimumOrderValue;
}
