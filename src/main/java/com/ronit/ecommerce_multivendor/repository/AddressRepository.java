package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByUser_Email(String email, Pageable pageable);
}
