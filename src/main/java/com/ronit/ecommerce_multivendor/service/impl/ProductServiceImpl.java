package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.ProductRequest;
import com.ronit.ecommerce_multivendor.dto.response.ProductResponse;
import com.ronit.ecommerce_multivendor.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public ProductResponse createProduct(String email, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(String email, Long productId, ProductRequest productRequest) {
        return null;
    }

    @Override
    public void deleteProduct(String email, Long productId) {

    }

    @Override
    public List<ProductResponse> getAllProducts(Pageable pageable) {
        return List.of();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return null;
    }

    @Override
    public List<ProductResponse> searchProducts(String title, String description, String color, String sizes, Integer minPrice, Integer maxPrice, Pageable pageable) {
        return List.of();
    }

    @Override
    public List<ProductResponse> getMyProducts(String email, Pageable pageable) {
        return List.of();
    }

    @Override
    public ProductResponse updateInventory(String email, Long productId, Integer quantity) {
        return null;
    }
}
