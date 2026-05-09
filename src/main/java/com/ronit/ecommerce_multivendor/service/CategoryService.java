package com.ronit.ecommerce_multivendor.service;

import com.ronit.ecommerce_multivendor.dto.request.CategoryRequest;
import com.ronit.ecommerce_multivendor.dto.response.CategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getAllCategories(Pageable pageable);
    List<CategoryResponse> getCategoriesByLevel(Integer level, Pageable pageable);
    List<CategoryResponse> getSubCategories(Long parentCategoryId);
    List<CategoryResponse> getRootCategories(Pageable pageable);

}
