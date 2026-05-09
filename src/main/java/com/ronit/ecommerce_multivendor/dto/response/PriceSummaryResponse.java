package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceSummaryResponse {
    private Integer totalMrpPrice;
    private Integer totalSellingPrice;
    private Integer discountAmount;
    private Integer shippingCharge;
    private Integer payableAmount;
}
