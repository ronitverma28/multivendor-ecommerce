package com.ronit.ecommerce_multivendor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Double discountPercentage;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private Double minimumOrderValue;

    private Boolean isActive = true;

    @ManyToMany(mappedBy = "usedCoupons")
    private Set<User> users = new HashSet<>();
}