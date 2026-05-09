package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setFrom(fromEmail);
            message.setText(body);
            mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
