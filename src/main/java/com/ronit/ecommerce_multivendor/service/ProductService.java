package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.ProductRequest;
import com.ronit.ecommerce_multivendor.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(String email, ProductRequest productRequest);
    ProductResponse updateProduct(String email, Long productId, ProductRequest productRequest);
    void deleteProduct(String email, Long productId);
    List<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse getProductById(Long productId);
    List<ProductResponse> searchProducts(String title, String description, String color, String sizes, Integer minPrice, Integer maxPrice, Pageable pageable);
    List<ProductResponse> getMyProducts(String email, Pageable pageable);
    ProductResponse updateInventory(String email, Long productId, Integer quantity);



}
