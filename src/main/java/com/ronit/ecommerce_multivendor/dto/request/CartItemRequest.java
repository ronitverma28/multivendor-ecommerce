package com.ronit.ecommerce_multivendor.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    @NotNull(message = "Product id is required")
    private Long productId;

    @NotBlank(message = "Size is required")
    @Pattern(regexp = "^(S|M|L|XL|XXL|XXXL|28|30|32|34|36|38|40)$")
    private String size;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;
}
