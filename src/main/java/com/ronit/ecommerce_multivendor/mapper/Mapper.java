package com.ronit.ecommerce_multivendor.mapper;

import com.ronit.ecommerce_multivendor.dto.response.*;
import com.ronit.ecommerce_multivendor.model.*;

import java.util.List;

public class Mapper {
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .build();
    }

    public static UserProfileResponse toProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .build();
    }

    public static AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .name(address.getName())
                .email(address.getUser().getEmail())
                .locality(address.getLocality())
                .address(address.getAddress())
                .city(address.getCity())
                .state(address.getState())
                .pinCode(address.getPinCode())
                .mobile(address.getMobile())
                .build();
    }

    public static SellerResponse toResponse(Seller seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .gstin(seller.getGstin())
                .accountHolderName(seller.getBankDetails().getAccountHolderName())
                .sellerName(seller.getSellerName())
                .accountNumber(seller.getBankDetails().getAccountNumber())
                .ifscCode(seller.getBankDetails().getIfscCode())
                .businessEmail(seller.getBusinessDetails().getBusinessEmail())
                .businessName(seller.getBusinessDetails().getBusinessName())
                .mobileNumber(seller.getMobileNumber())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        ParentCategoryResponse parentCategoryResponse = (category.getParentCategory() != null) ? toParentCategoryResponse(category) : null;
        List<CategoryResponse> subCategories = category.getSubCategories().stream().map(Mapper::toResponseWithoutChildren).toList();

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .level(category.getLevel())
                .parentCategory(parentCategoryResponse)
                .subCategories(subCategories)
                .build();
    }

    public static CategoryResponse toResponseWithoutChildren(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryId(category.getCategoryId())
                .level(category.getLevel())
                .name(category.getName())
                .build();
    }

    public static ParentCategoryResponse toParentCategoryResponse(Category category) {
        return ParentCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .mrpPrice(product.getMrpPrice())
                .sellingPrice(product.getSellingPrice())
                .discountPercent(product.getDiscountPercent())
                .quantity(product.getQuantity())
                .color(product.getColor())
                .sizes(product.getSizes())
                .images(product.getImages())
                .numRating(product.getNumRating())
                .category(toParentCategoryResponse(product.getCategory()))
                .seller(toSellerSummary(product.getSeller()))
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static SellerSummary toSellerSummary(Seller seller) {
        return SellerSummary.builder()
                .id(seller.getId())
                .gstin(seller.getGstin())
                .sellerName(seller.getSellerName())
                .build();
    }

    public static SellerReportResponse toResponse(SellerReport sellerReport) {
        return SellerReportResponse.builder()
                .totalEarnings(sellerReport.getTotalEarnings())
                .totalSales(sellerReport.getTotalSales())
                .totalRefunds(sellerReport.getTotalRefunds())
                .totalTax(sellerReport.getTotalTax())
                .netEarnings(sellerReport.getNetEarnings())
                .totalOrders(sellerReport.getTotalOrders())
                .canceledOrders(sellerReport.getCanceledOrders())
                .totalTransactions(sellerReport.getTotalTransactions())
                .build();
    }

    public static CartResponse toResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cart.getCartItems().stream().map(Mapper::toResponse).toList())
                .totalSellingPrice(cart.getTotalSellingPrice())
                .totalMrpPrice(cart.getTotalMrpPrice())
                .totalItem(cart.getTotalItem())
                .discount(cart.getDiscount())
                .couponCode(cart.getCouponCode())
                .build();
    }

    public static CartItemResponse toResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productTitle(cartItem.getProduct().getTitle())
                .productImage(
                        cartItem.getProduct().getImages() != null &&
                                !cartItem.getProduct().getImages().isEmpty()
                                ? cartItem.getProduct().getImages().getFirst()
                                : null
                )
                .size(cartItem.getSize())
                .quantity(cartItem.getQuantity())
                .mrpPrice(cartItem.getMrpPrice())
                .sellingPrice(cartItem.getSellingPrice())
                .lineTotal(cartItem.getSellingPrice())
                .build();
    }
}
