package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.PaymentMethod;
import com.ronit.ecommerce_multivendor.model.enums.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateOrderResponse {
    private Long paymentOrderId;
    private String gatewayOrderId;
    private String paymentLinkId;
    private String paymentPageUrl;
    private PaymentMethod paymentMethod;
    private PaymentOrderStatus status;
    private Long amount;
    private String currency;
    private LocalDateTime createdAt;
}
