package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.UserRequest;
import com.ronit.ecommerce_multivendor.dto.response.UserProfileResponse;
import com.ronit.ecommerce_multivendor.dto.response.UserResponse;
import com.ronit.ecommerce_multivendor.service.UserService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PermitAll
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(ApiResponse.ok("Customer registered successfully", userService.create(userRequest)));
    }

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<UserResponse>> update(Authentication authentication, @Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(ApiResponse.ok("Customer updated successfully", userService.update(authentication.getName(), userRequest)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@RequestParam String email){
        return ResponseEntity.ok(ApiResponse.ok("Customer fetched successfully", userService.getByEmail(email)));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String email){
        userService.delete(email);
        return ResponseEntity.ok(ApiResponse.ok("Customer deleted successfully", null));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("Customers fetched successfully", userService.getAll(pageable)));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> profile(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.ok("User profile fetched successfully", userService.getUserProfile(authentication.getName())));
    }
}
