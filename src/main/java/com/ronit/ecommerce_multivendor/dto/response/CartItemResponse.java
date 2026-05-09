package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productTitle;
    private String productImage;
    private String size;
    private Integer quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Integer lineTotal;
}
