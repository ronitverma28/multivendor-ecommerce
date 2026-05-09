package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSellerApproveResponse {
    private Long sellerId;
    private String sellerName;
    private String sellerEmail;
    private AccountStatus previousStatus;
    private AccountStatus currentStatus;
    private String remarks;
}
