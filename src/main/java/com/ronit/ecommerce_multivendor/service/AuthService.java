package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.LoginRequest;
import com.ronit.ecommerce_multivendor.dto.response.LoginResponse;


public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);



}
