package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.PaymentMethod;
import com.ronit.ecommerce_multivendor.model.enums.PaymentOrderStatus;
import com.ronit.ecommerce_multivendor.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSummaryResponse {
    private Long paymentOrderId;
    private String paymentId;
    private String gatewayOrderId;
    private String paymentLinkId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private PaymentOrderStatus paymentOrderStatus;
}
