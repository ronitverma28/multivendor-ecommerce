package com.ronit.ecommerce_multivendor.service.impl;
import com.ronit.ecommerce_multivendor.dto.request.LoginRequest;
import com.ronit.ecommerce_multivendor.dto.response.LoginResponse;
import com.ronit.ecommerce_multivendor.exception.BadRequestException;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.repository.VerificationCodeRepository;
import com.ronit.ecommerce_multivendor.security.jwt.JwtService;
import com.ronit.ecommerce_multivendor.service.AuthService;
import com.ronit.ecommerce_multivendor.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));

        if(user.getAccountStatus() != AccountStatus.ACTIVE) throw new BadRequestException("Account is not active. Please verify your email.");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new LoginResponse(jwtService.generateToken(userDetails), userDetails.getUsername(), user.getFirstName(), user.getLastName(), user.getMobileNumber(), user.getRole());
    }
}