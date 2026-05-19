package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponse {
    private Long id;
    private String code;
    private Double discountPercentage;
    private Boolean active;
}
