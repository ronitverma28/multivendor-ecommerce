package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.AddressRequest;
import com.ronit.ecommerce_multivendor.dto.response.AddressResponse;
import com.ronit.ecommerce_multivendor.exception.BadRequestException;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.Address;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.repository.AddressRepository;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public AddressResponse create(String email, AddressRequest addressRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Address address = Address.builder()
                .address(addressRequest.getAddress())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .locality(addressRequest.getLocality())
                .user(user)
                .name(addressRequest.getName())
                .pinCode(addressRequest.getPinCode())
                .mobile(addressRequest.getMobile())
                .build();

        return Mapper.toResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse update(String email, Long addressId, AddressRequest addressRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        address.setAddress(addressRequest.getAddress());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setName(addressRequest.getName());
        address.setPinCode(addressRequest.getPinCode());
        address.setMobile(addressRequest.getMobile());
        address.setLocality(addressRequest.getLocality());

        return Mapper.toResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse findMyAddress(String email, Long addressId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        if(!address.getUser().getEmail().equals(email)) throw new ResourceNotFoundException("Address not found with id: " + addressId);
        return Mapper.toResponse(address);
    }

    @Override
    public void delete(String email, Long addressId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        if(!address.getUser().getEmail().equals(user.getEmail())) throw new BadRequestException("Address not mapped to user with email: " + email);
        addressRepository.delete(address);
    }

    @Override
    public List<AddressResponse> findAllMyAddresses(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return addressRepository.findAllByUser_Email(user.getEmail(), pageable).stream().map(Mapper::toResponse).toList();
    }
}
