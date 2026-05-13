package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.SellerRequest;
import com.ronit.ecommerce_multivendor.dto.response.SellerReportResponse;
import com.ronit.ecommerce_multivendor.dto.response.SellerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SellerService {
    SellerResponse createSeller(String email, SellerRequest sellerRequest);
    SellerResponse getSellerByEmail(String email);
    List<SellerResponse> getAllSellers(Pageable pageable);
    SellerResponse updateSellerProfile(String email, SellerRequest sellerRequest);
    void deleteSeller(String email);
    void deleteSellerByEmail(String email);
    void updateSellerStatus(String email);
    SellerResponse getSellerProfile(String email);
    SellerReportResponse getSellerReport(String email);
}
