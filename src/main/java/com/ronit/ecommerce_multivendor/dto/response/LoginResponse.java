package com.ronit.ecommerce_multivendor.dto.response;

import com.ronit.ecommerce_multivendor.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private UserRole role;
}
