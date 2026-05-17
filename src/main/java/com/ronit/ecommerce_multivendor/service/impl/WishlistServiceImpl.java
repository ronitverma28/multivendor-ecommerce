package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.response.WishlistResponse;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.Product;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.repository.ProductRepository;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public WishlistResponse getWishlist(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(user.getWishlist().getProducts().isEmpty()) throw new ResourceNotFoundException("No Products added");
        return Mapper.toResponse(user.getWishlist());
    }

    @Override
    public WishlistResponse addProductToWishlist(String email, Long productId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        user.getWishlist().getProducts().add(product);
        return Mapper.toResponse(userRepository.save(user).getWishlist());
    }

    @Override
    public void removeProductFromWishlist(String email, Long productId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if(user.getWishlist().getProducts().isEmpty()) throw new ResourceNotFoundException("Wishlist is empty, Can't delete any product");
        user.getWishlist().getProducts().remove(product);
        userRepository.save(user);
    }
}
