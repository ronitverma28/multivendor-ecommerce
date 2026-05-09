package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.UserRequest;
import com.ronit.ecommerce_multivendor.dto.response.UserProfileResponse;
import com.ronit.ecommerce_multivendor.dto.response.UserResponse;
import com.ronit.ecommerce_multivendor.exception.DuplicateResourceException;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.repository.VerificationCodeRepository;
import com.ronit.ecommerce_multivendor.service.AuthService;
import com.ronit.ecommerce_multivendor.service.EmailService;
import com.ronit.ecommerce_multivendor.service.OtpService;
import com.ronit.ecommerce_multivendor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final OtpService otpService;
    private final EmailService emailService;

    @Override
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new DuplicateResourceException("Email already exists");
        if (userRepository.existsByMobileNumber(userRequest.getMobileNumber()))
            throw new DuplicateResourceException("Phone number already exists");

        User user = User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .mobileNumber(userRequest.getMobileNumber())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

//      Send OTP to user's email here using an email service
        otpService.sendOtpEmail(savedUser.getEmail());

        return Mapper.toResponse(savedUser);
    }

    @Override
    public UserResponse update(String email, UserRequest userRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        if (!user.getMobileNumber().equals(userRequest.getMobileNumber()) && userRepository.existsByMobileNumber(userRequest.getMobileNumber()))
            throw new DuplicateResourceException("Phone number already exists");

        user.setMobileNumber(userRequest.getMobileNumber());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return Mapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        return Mapper.toResponse(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email)));
    }

    @Override
    public List<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent().stream().map(Mapper::toResponse).toList();
    }

    @Override
    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + email));
        return Mapper.toProfileResponse(user);
    }
}
