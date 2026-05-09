package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.LoginRequest;
import com.ronit.ecommerce_multivendor.dto.response.LoginResponse;
import com.ronit.ecommerce_multivendor.service.AuthService;
import com.ronit.ecommerce_multivendor.service.OtpService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(ApiResponse.ok("Login successful", authService.login(loginRequest)));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String email, @RequestParam String otp){
        otpService.verifyOtp(email, otp);
        return ResponseEntity.ok(ApiResponse.ok("Email verification successful", null));
    }

    @GetMapping("/resend-verification-email")
    public ResponseEntity<ApiResponse<Void>> resendVerificationEmail(@RequestParam String email){
        otpService.resendOtp(email);
        return ResponseEntity.ok(ApiResponse.ok("Verification email resent successfully", null));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(){
        // Invalidate the JWT token on the client side (e.g., by removing it from local storage)
        return ResponseEntity.ok(ApiResponse.ok("Logout successful", null));
    }


}
