package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.CartItemRequest;
import com.ronit.ecommerce_multivendor.dto.response.CartResponse;

public interface CartService {
    CartResponse getMyCart(String email);
    CartResponse addToCart(String email, CartItemRequest cartItemRequest);
    CartResponse updateCartItem(String email, Long cartItemId, Integer quantity);
    void deleteItemFromCart(String email, Long cartItemId);
    void clearCart(String email);
}
