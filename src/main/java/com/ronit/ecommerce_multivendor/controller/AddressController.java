package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.AddressRequest;
import com.ronit.ecommerce_multivendor.dto.response.AddressResponse;
import com.ronit.ecommerce_multivendor.service.AddressService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> create(Authentication authentication, @Valid @RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(ApiResponse.ok("Address added successfully", addressService.create(authentication.getName(), addressRequest)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AddressResponse>> update(Authentication authentication, @RequestParam Long addressId, @Valid @RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(ApiResponse.ok("Address updated successfully", addressService.update(authentication.getName(), addressId, addressRequest)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AddressResponse>> findMyAddress(Authentication authentication, @RequestParam Long addressId){
        return ResponseEntity.ok(ApiResponse.ok("Address fetched successfully", addressService.findMyAddress(authentication.getName(), addressId)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(Authentication authentication, @RequestParam Long addressId){
        addressService.delete(authentication.getName(), addressId);
        return ResponseEntity.ok(ApiResponse.ok("Address deleted successfully", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> findAllMyAddresses(Authentication authentication, @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("Addresses fetched successfully", addressService.findAllMyAddresses(authentication.getName(), pageable)));
    }
}
