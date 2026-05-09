package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private LocalDateTime expiresAt;
    private String email;
    private UserRole role;
    private AccountStatus accountStatus;
    private String message;
}
