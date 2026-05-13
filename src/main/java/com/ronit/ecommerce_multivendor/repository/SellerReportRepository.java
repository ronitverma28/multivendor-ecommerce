package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Category;
import com.ronit.ecommerce_multivendor.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    Optional<SellerReport> findBySeller_User_Email(String email);
}
