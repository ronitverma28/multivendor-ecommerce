package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerifyResponse {
    private boolean signatureValid;
    private PaymentStatus paymentStatus;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private LocalDateTime verifiedAt;
    private String message;
}
