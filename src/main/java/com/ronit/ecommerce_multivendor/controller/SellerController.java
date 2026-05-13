package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.SellerRequest;
import com.ronit.ecommerce_multivendor.dto.response.SellerReportResponse;
import com.ronit.ecommerce_multivendor.dto.response.SellerResponse;
import com.ronit.ecommerce_multivendor.service.SellerService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
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
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<SellerResponse>> createSeller(Authentication authentication, @Valid @RequestBody SellerRequest sellerRequest) {
        return ResponseEntity.ok(ApiResponse.ok("Seller created successfully", sellerService.createSeller(authentication.getName(), sellerRequest)));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<SellerResponse>> getSellerProfile(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.ok("Seller profile fetched successfully", sellerService.getSellerProfile(authentication.getName())));
    }

    @GetMapping("/report")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<SellerReportResponse>> getSellerReport(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.ok("Seller Report fetched successfully", sellerService.getSellerReport(authentication.getName())));
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SellerResponse>> getSellerByEmail(@PathVariable String email){
        return ResponseEntity.ok(ApiResponse.ok("Seller fetched successfully", sellerService.getSellerByEmail(email)));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SellerResponse>>> getAllSellers(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Sellers fetched successfully", sellerService.getAllSellers(pageable)));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<SellerResponse>> updateSeller(Authentication authentication, @Valid @RequestBody SellerRequest sellerRequest){
        return ResponseEntity.ok(ApiResponse.ok("Seller updated successfully", sellerService.updateSellerProfile(authentication.getName(), sellerRequest)));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Void>> deleteSeller(Authentication authentication){
        sellerService.deleteSeller(authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok("Seller deleted successfully", null));
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSellerByEmail(@PathVariable String email){
        sellerService.deleteSellerByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok("Seller deleted successfully", null));
    }

    @PatchMapping("/status/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateSellerStatus(@PathVariable String email){
        sellerService.updateSellerStatus(email);
        return ResponseEntity.ok(ApiResponse.ok("Seller verified successfully", null));
    }
}
