package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.SellerRequest;
import com.ronit.ecommerce_multivendor.dto.response.SellerResponse;
import com.ronit.ecommerce_multivendor.exception.BadRequestException;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.*;
import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.model.enums.UserRole;
import com.ronit.ecommerce_multivendor.repository.AddressRepository;
import com.ronit.ecommerce_multivendor.repository.SellerRepository;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public SellerResponse create(String email, Long addressId, SellerRequest sellerRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (sellerRepository.existsByUser_Email(email)) {
            throw new BadRequestException("Seller profile already exists for user: " + email);
        }

        Address pickupAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        if (!pickupAddress.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Pickup address does not belong to authenticated user");
        }

        Seller seller = new Seller();
        seller.setUser(user);
        seller.setSellerName(sellerRequest.getSellerName());
        seller.setMobileNumber(sellerRequest.getMobileNumber());
        seller.setGstin(sellerRequest.getGstin());
        seller.setPickupAddress(pickupAddress);
        seller.setBusinessDetails(new BusinessDetails(
                sellerRequest.getBusinessName(),
                sellerRequest.getBusinessEmail(),
                sellerRequest.getBusinessMobile(),
                sellerRequest.getBusinessAddress(),
                sellerRequest.getLogo(),
                sellerRequest.getBanner()
        ));
        seller.setBankDetails(new BankDetails(
                sellerRequest.getAccountNumber(),
                sellerRequest.getAccountHolderName(),
                sellerRequest.getIfscCode()
        ));

        user.setRole(UserRole.ROLE_SELLER);
        userRepository.save(user);

        return Mapper.toResponse(sellerRepository.save(seller));
    }

    @Override
    public SellerResponse getByEmail(String email) {
        Seller seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + email));
        return Mapper.toResponse(seller);
    }

    @Override
    public List<SellerResponse> getAllSellers(Pageable pageable) {
        return sellerRepository.findAll(pageable)
                .stream()
                .map(Mapper::toResponse)
                .toList();
    }

    @Override
    public SellerResponse update(String email, Long addressId, SellerRequest sellerRequest) {
        Seller seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + email));

        Address pickupAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        if (!pickupAddress.getUser().getEmail().equals(email)) {
            throw new BadRequestException("Pickup address does not belong to authenticated seller");
        }

        seller.setSellerName(sellerRequest.getSellerName());
        seller.setMobileNumber(sellerRequest.getMobileNumber());
        seller.setGstin(sellerRequest.getGstin());
        seller.setPickupAddress(pickupAddress);
        seller.setBusinessDetails(new BusinessDetails(
                sellerRequest.getBusinessName(),
                sellerRequest.getBusinessEmail(),
                sellerRequest.getBusinessMobile(),
                sellerRequest.getBusinessAddress(),
                sellerRequest.getLogo(),
                sellerRequest.getBanner()
        ));
        seller.setBankDetails(new BankDetails(
                sellerRequest.getAccountNumber(),
                sellerRequest.getAccountHolderName(),
                sellerRequest.getIfscCode()
        ));

        return Mapper.toResponse(sellerRepository.save(seller));
    }

    @Override
    public void delete(String email) {
        Seller seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + email));
        User user = seller.getUser();

        sellerRepository.delete(seller);
        user.setRole(UserRole.ROLE_CUSTOMER);
        userRepository.save(user);
    }

    @Override
    public void deleteByEmail(String email) {
        Seller seller = sellerRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + email));
        User user = seller.getUser();

        sellerRepository.delete(seller);
        user.setRole(UserRole.ROLE_CUSTOMER);
        userRepository.save(user);
    }

    @Override
    public void verifySeller(String email) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with email: " + email));
        boolean isSellerDetailsCorrect = true;
        if (isSellerDetailsCorrect) {
            seller.getUser().setRole(UserRole.ROLE_SELLER);
            seller.setAccountStatus(AccountStatus.ACTIVE);
            sellerRepository.save(seller);
        }
    }

}
