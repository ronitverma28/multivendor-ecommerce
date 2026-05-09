package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.SellerRequest;
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
    public ResponseEntity<ApiResponse<SellerResponse>> create(Authentication authentication, @RequestParam Long addressId, @Valid @RequestBody SellerRequest sellerRequest) {
        return ResponseEntity.ok(ApiResponse.ok("Seller created successfully", sellerService.create(authentication.getName(), addressId, sellerRequest)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SellerResponse>> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(ApiResponse.ok("Seller fetched successfully", sellerService.getByEmail(email)));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SellerResponse>>> getAllSellers(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Sellers fetched successfully", sellerService.getAllSellers(pageable)));
    }

    @PutMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<SellerResponse>> update(Authentication authentication, @RequestParam Long addressId, @Valid @RequestBody SellerRequest sellerRequest){
        return ResponseEntity.ok(ApiResponse.ok("Seller updated successfully", sellerService.update(authentication.getName(), addressId, sellerRequest)));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<Void>> delete(Authentication authentication){
        sellerService.delete(authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok("Seller deleted successfully", null));
    }

    @DeleteMapping("/by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteByEmail(@RequestParam String email){
        sellerService.deleteByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok("Seller deleted successfully", null));
    }

    @GetMapping("/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> verifySeller(@RequestParam String email){
        sellerService.verifySeller(email);
        return ResponseEntity.ok(ApiResponse.ok("Seller verified successfully", null));
    }
    
}
