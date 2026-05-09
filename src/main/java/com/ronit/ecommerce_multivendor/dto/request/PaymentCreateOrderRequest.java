package com.ronit.ecommerce_multivendor.dto.request;

import com.ronit.ecommerce_multivendor.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
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
public class PaymentCreateOrderRequest {
    @NotEmpty(message = "At least one order id is required")
    @Builder.Default
    private List<Long> orderIds = new ArrayList<>();

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String returnUrl;
}
