package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.ProductRequest;
import com.ronit.ecommerce_multivendor.dto.response.ProductResponse;
import com.ronit.ecommerce_multivendor.service.ProductService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(Authentication authentication, @Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(ApiResponse.ok("Product created successfully", productService.createProduct(authentication.getName(), productRequest)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(Authentication authentication, @PathVariable Long id, @Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(ApiResponse.ok("Product updated successfully", productService.updateProduct(authentication.getName(), id, productRequest)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(Authentication authentication, @PathVariable Long id){
        productService.deleteProduct(authentication.getName(), id);
        return ResponseEntity.ok(ApiResponse.ok("Product deleted successfully", null));
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(@PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Product fetched", productService.getAllProducts(pageable)));
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.ok("Product fetched successfully", productService.getProductById(id)));
    }

    @GetMapping("/search")
    @PermitAll
    public ResponseEntity<ApiResponse<List<ProductResponse>>> searchProducts(@RequestParam(required = false) String title, @RequestParam(required = false) String description, @RequestParam(required = false) String color, @RequestParam(required = false) String sizes, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice, @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("Product fetched successfully", productService.searchProducts(title, description, color, sizes, minPrice, maxPrice, pageable)));
    }

    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getMyProducts(Authentication authentication, @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Products fetched successfully", productService.getMyProducts(authentication.getName(), pageable)));
    }

    @PatchMapping("/{id}/quantity")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateInventory(Authentication authentication, @PathVariable Long id, @Valid @Min(value = 0, message = "Cannot set quantity less than 0") @RequestBody Integer quantity){
        return ResponseEntity.ok(ApiResponse.ok("Product quantity updated successfully", productService.updateInventory(authentication.getName(), id, quantity)));
    }

}
