package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.OrderStatus;
import com.ronit.ecommerce_multivendor.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderId;
    private Long userId;
    private Long sellerId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

    @Builder.Default
    private List<OrderItemResponse> items = new ArrayList<>();

    private AddressSnapshotResponse shippingAddress;
    private PriceSummaryResponse priceSummary;
    private String couponCode;
    private PaymentSummaryResponse payment;
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
}
