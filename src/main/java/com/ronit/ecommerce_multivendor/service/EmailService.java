package com.ronit.ecommerce_multivendor.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
