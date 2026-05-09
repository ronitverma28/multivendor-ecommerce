package com.ronit.ecommerce_multivendor.service;

public interface OtpService {
    String generateOtp();
    void sendOtpEmail(String email);
    void verifyOtp(String email, String otp);
    void resendOtp(String email);

}
