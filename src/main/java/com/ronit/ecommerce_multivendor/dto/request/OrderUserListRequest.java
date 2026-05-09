package com.ronit.ecommerce_multivendor.dto.request;

import com.ronit.ecommerce_multivendor.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserListRequest {
    private OrderStatus status;
    private Integer page;
    private Integer size;
}
