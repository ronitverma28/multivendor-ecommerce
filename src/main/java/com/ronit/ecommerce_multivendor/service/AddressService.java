package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.AddressRequest;
import com.ronit.ecommerce_multivendor.dto.response.AddressResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressResponse create(String email, AddressRequest addressRequest);
    AddressResponse update(String email, Long address, AddressRequest addressRequest);
    AddressResponse findMyAddress(String email, Long addressId);
    void delete(String email, Long addressId);
    List<AddressResponse> findAllMyAddresses(String email, Pageable pageable);
}
