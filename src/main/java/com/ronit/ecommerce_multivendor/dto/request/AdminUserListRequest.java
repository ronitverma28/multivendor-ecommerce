package com.ronit.ecommerce_multivendor.dto.request;

import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserListRequest {
    private String query;
    private UserRole role;
    private AccountStatus accountStatus;
    private Integer page;
    private Integer size;
}
