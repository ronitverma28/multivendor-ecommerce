package com.ronit.ecommerce_multivendor.dto.request;

import com.ronit.ecommerce_multivendor.model.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class OrderCreateRequest {
    @NotNull(message = "Shipping address id is required")
    private Long shippingAddressId;

    private String couponCode;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Valid
    @Builder.Default
    private List<OrderCreateItemRequest> items = new ArrayList<>();
}
