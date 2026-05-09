package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findTopByEmailOrderByIdDesc(String email);

    Long countByEmailAndCreatedAtAfter(String email, LocalDateTime oneHourAgo);

    int deleteByUsedTrueOrExpiryTimeBefore(LocalDateTime now);
}
