package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.response.WishlistResponse;
import com.ronit.ecommerce_multivendor.service.WishlistService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<ApiResponse<WishlistResponse>> getWishlist(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.ok("Wishlist fetched successfully", wishlistService.getWishlist(authentication.getName())));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<WishlistResponse>> addProductToWishlist(Authentication authentication,@PathVariable Long productId){
        return ResponseEntity.ok(ApiResponse.ok("Product added to wishlist successfully", wishlistService.addProductToWishlist(authentication.getName(), productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeProductFromWishlist(Authentication authentication, @PathVariable Long productId){
        wishlistService.removeProductFromWishlist(authentication.getName(), productId);
        return ResponseEntity.ok(ApiResponse.ok("Product deleted successfully", null));
    }
}
