package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.SellerRequest;
import com.ronit.ecommerce_multivendor.dto.response.SellerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {
    SellerResponse create(String email, Long addressId, SellerRequest sellerRequest);
    SellerResponse getByEmail(String email);
    List<SellerResponse> getAllSellers(Pageable pageable);
    SellerResponse update(String email, Long addressId, SellerRequest sellerRequest);
    void delete(String email);
    void deleteByEmail(String email);
    void verifySeller(String email);
}
