package com.ronit.ecommerce_multivendor.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRemoveRequest {
    @NotNull(message = "Cart item id is required")
    private Long cartItemId;
}
