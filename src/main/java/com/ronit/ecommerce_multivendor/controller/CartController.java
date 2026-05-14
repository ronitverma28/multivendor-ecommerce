package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.CartItemRequest;
import com.ronit.ecommerce_multivendor.dto.response.CartResponse;
import com.ronit.ecommerce_multivendor.service.CartService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getMyCart(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.ok("Cart fetched successfully", cartService.getMyCart(authentication.getName())));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(Authentication authentication,@Valid @RequestBody CartItemRequest cartItemRequest){
        return ResponseEntity.ok(ApiResponse.ok("Item added successfully to the cart", cartService.addToCart(authentication.getName(), cartItemRequest)));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(Authentication authentication, @PathVariable Long id, @Min(value = 1, message = "Quantity must be greater than 0") @RequestBody Integer quantity){
        return ResponseEntity.ok(ApiResponse.ok("Cart item updated", cartService.updateCartItem(authentication.getName(), id, quantity)));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItemFromCart(Authentication authentication, @PathVariable Long id){
        cartService.deleteItemFromCart(authentication.getName(), id);
        return ResponseEntity.ok(ApiResponse.ok("Item deleted from the cart",null ));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication){
        cartService.clearCart(authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok("Cart cleared successfully", null));
    }
}
