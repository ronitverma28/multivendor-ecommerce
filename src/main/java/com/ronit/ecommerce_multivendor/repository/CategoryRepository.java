package com.ronit.ecommerce_multivendor.repository;

import com.ronit.ecommerce_multivendor.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByLevel(Integer level, Pageable pageable);
    List<Category> findByParentCategory_Id(Long parentCategoryId);
}
