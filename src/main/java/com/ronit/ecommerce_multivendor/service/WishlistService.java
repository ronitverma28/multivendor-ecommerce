package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.response.WishlistResponse;

public interface WishlistService {
    WishlistResponse getWishlist(String email);
    WishlistResponse addProductToWishlist(String email, Long productId);
    void removeProductFromWishlist(String email, Long productId);
}
