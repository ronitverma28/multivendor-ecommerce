package com.ronit.ecommerce_multivendor.dto.request;

import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSellerApproveRequest {
    @NotNull(message = "Seller id is required")
    private Long sellerId;

    @NotNull(message = "Target account status is required")
    private AccountStatus targetStatus;

    private String remarks;
}
