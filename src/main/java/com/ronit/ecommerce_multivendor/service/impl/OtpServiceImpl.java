package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.model.User;
import com.ronit.ecommerce_multivendor.model.VerificationCode;
import com.ronit.ecommerce_multivendor.model.enums.AccountStatus;
import com.ronit.ecommerce_multivendor.repository.UserRepository;
import com.ronit.ecommerce_multivendor.repository.VerificationCodeRepository;
import com.ronit.ecommerce_multivendor.service.EmailService;
import com.ronit.ecommerce_multivendor.service.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Component
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 🔥 Configurable limits
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int COOLDOWN_SECONDS = 5;
    private static final int MAX_REQUESTS_PER_HOUR = 50;

    @Override
    public void sendOtpEmail(String email) {
        User user = getUserByEmail(email);

        validateRateLimit(email);

        String otp = generateOtp();

        saveOtp(user, otp);

        sendEmail(user, otp);
    }

    @Override
    public void resendOtp(String email) {
        sendOtpEmail(email);
    }

    @Override
    public void verifyOtp(String email, String otp) {
        VerificationCode verificationCode = verificationCodeRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new ResourceNotFoundException("No OTP found"));

        if (verificationCode.isUsed()) {
            throw new RuntimeException("OTP already used");
        }

        if (verificationCode.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!passwordEncoder.matches(otp, verificationCode.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);

        User user = verificationCode.getUser();
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
    }

    // ================= HELPER METHODS =================

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void validateRateLimit(String email) {
        VerificationCode latestOtp = verificationCodeRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElse(null);

        if (latestOtp != null &&
                latestOtp.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(COOLDOWN_SECONDS))) {
            throw new RuntimeException("Please wait before requesting another OTP");
        }

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        Long count = verificationCodeRepository.countByEmailAndCreatedAtAfter(email, oneHourAgo);

        if (count >= MAX_REQUESTS_PER_HOUR) {
            throw new RuntimeException("Too many OTP requests. Try again later.");
        }
    }

    private void saveOtp(User user, String otp) {
        VerificationCode verificationCode = VerificationCode.builder()
                .otp(passwordEncoder.encode(otp))
                .email(user.getEmail())
                .expiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .user(user)
                .build();

        verificationCodeRepository.save(verificationCode);
    }

    private void sendEmail(User user, String otp) {
        String subject = "Your OTP Code";
        String body = "Hello " + user.getFirstName() +
                ",\n\nYour OTP is: " + otp +
                "\nExpires in " + OTP_EXPIRY_MINUTES + " minutes.";

        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Override
    public String generateOtp() {
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    @Transactional
    public void cleanExpiredAndUsedOtps() {

        int deletedCount = verificationCodeRepository
                .deleteByUsedTrueOrExpiryTimeBefore(LocalDateTime.now());

        log.info("Deleted {} expired/used OTPs", deletedCount);
    }
}