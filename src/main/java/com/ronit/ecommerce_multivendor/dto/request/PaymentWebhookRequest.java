package com.ronit.ecommerce_multivendor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookRequest {
    @NotBlank(message = "Event is required")
    private String event;

    @NotBlank(message = "Raw payload is required")
    private String payload;

    @NotBlank(message = "Webhook signature is required")
    private String signature;
}
