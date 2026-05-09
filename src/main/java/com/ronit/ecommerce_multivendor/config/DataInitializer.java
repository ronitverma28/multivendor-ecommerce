package com.ronit.ecommerce_multivendor.config;

import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.model.enums.UserRole;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin.owner@gmail.com";
        String adminPassword = "admin123";

        if(!userRepository.existsByEmail(adminEmail)){
            userRepository.save(User.builder()
                    .email(adminEmail)
                    .firstName("Admin")
                    .lastName("Owner")
                    .role(UserRole.ROLE_ADMIN)
                    .accountStatus(AccountStatus.ACTIVE)
                    .password(passwordEncoder.encode(adminPassword))
                    .build());
        }
    }
}
