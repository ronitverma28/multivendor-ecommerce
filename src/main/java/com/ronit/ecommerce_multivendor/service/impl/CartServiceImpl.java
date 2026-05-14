package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.CartItemRequest;
import com.ronit.ecommerce_multivendor.dto.response.CartResponse;
import com.ronit.ecommerce_multivendor.exception.BadRequestException;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.Cart;
import com.ronit.ecommerce_multivendor.model.CartItem;
import com.ronit.ecommerce_multivendor.model.Product;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.repository.CartItemRepository;
import com.ronit.ecommerce_multivendor.repository.CartRepository;
import com.ronit.ecommerce_multivendor.repository.ProductRepository;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponse getMyCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Customer not found with email : " + email));
        Cart cart = cartRepository.findByUser_Id(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return Mapper.toResponse(cart);
    }

    @Override
    public CartResponse addToCart(String email, CartItemRequest cartItemRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Customer not found with email : " + email));
        Product product = productRepository.findById(cartItemRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + cartItemRequest.getProductId()));
        Cart cart = cartRepository.findByUser_Id(user.getId()).orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        CartItem existingCartItem = cartItemRepository.findByCartAndProductAndSize(cart, product, cartItemRequest.getSize());
        CartItem cartItem;
        if(existingCartItem != null){
            Integer updatedQuantity = cartItemRequest.getQuantity() + existingCartItem.getQuantity();
            existingCartItem.setQuantity(updatedQuantity);
            existingCartItem.setMrpPrice(updatedQuantity * product.getMrpPrice());
            existingCartItem.setSellingPrice(updatedQuantity * product.getSellingPrice());
            cartItem = existingCartItem;
        }else{
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .size(cartItemRequest.getSize())
                    .quantity(cartItemRequest.getQuantity())
                    .mrpPrice(product.getMrpPrice() * cartItemRequest.getQuantity())
                    .sellingPrice(product.getSellingPrice() * cartItemRequest.getQuantity())
                    .userId(user.getId())
                    .build();
        }

        cartItemRepository.save(cartItem);
        cart.getCartItems().add(cartItem);
        return Mapper.toResponse(updateCartPrices(cart));
    }

    @Override
    public CartResponse updateCartItem(String email, Long cartItemId, Integer quantity) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Customer not found with email : " + email));
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Item not found in the card with id : " + cartItemId));
        cartItem.setQuantity(quantity);
        cartItem.setMrpPrice(cartItem.getProduct().getMrpPrice() * quantity);
        cartItem.setSellingPrice(cartItem.getProduct().getSellingPrice() * quantity);
        cartItemRepository.save(cartItem);
        return Mapper.toResponse(updateCartPrices(cartItem.getCart()));
    }

    @Override
    public void deleteItemFromCart(String email, Long cartItemId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Item not found in the cart"));
        if(!cartItem.getCart().getUser().getId().equals(user.getId())) throw new BadRequestException("Item not present in the cart");
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        updateCartPrices(cart);
    }

    @Override
    public void clearCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();
        if(cart == null || cart.getCartItems().isEmpty()) throw new ResourceNotFoundException("Cart is already empty");
        cart.getCartItems().clear();
        cart.setCouponCode(null);
        cart.setDiscount(0.0);
        cart.setTotalItem(0);
        cart.setTotalSellingPrice(0.0);
        cart.setTotalMrpPrice(0);
        cartRepository.save(cart);

    }

    private Cart updateCartPrices(Cart cart){
        Integer totalItem = 0;
        Integer totalMrpPrice = 0;
        Double totalSellingPrice = 0.0;

        for(CartItem cartItem : cart.getCartItems()){
            totalItem += cartItem.getQuantity();
            totalMrpPrice += cartItem.getMrpPrice();
            totalSellingPrice += cartItem.getSellingPrice();
        }

        cart.setTotalItem(totalItem);
        cart.setTotalMrpPrice(totalMrpPrice);
        cart.setTotalSellingPrice(totalSellingPrice);
        cart.setDiscount(totalMrpPrice - totalSellingPrice);
        return cartRepository.save(cart);
    }
}
