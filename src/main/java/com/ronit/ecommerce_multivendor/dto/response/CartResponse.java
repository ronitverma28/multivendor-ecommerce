package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartId;

    @Builder.Default
    private List<CartItemResponse> items = new ArrayList<>();

    private PriceSummaryResponse priceSummary;
    private String couponCode;
}
