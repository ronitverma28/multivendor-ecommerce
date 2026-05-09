package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.CategoryRequest;
import com.ronit.ecommerce_multivendor.dto.response.CategoryResponse;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.Category;
import com.ronit.ecommerce_multivendor.repository.CategoryRepository;
import com.ronit.ecommerce_multivendor.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category parent = (categoryRequest.getParentCategoryId() == null) ? null : categoryRepository.findById(categoryRequest.getParentCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id : " + categoryRequest.getParentCategoryId()));

        Category category = Category.builder().name(categoryRequest.getName()).level(categoryRequest.getLevel()).parentCategory(parent).categoryId(UUID.randomUUID().toString()).build();

        return Mapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId));
        category.setName(categoryRequest.getName());
        category.setLevel(categoryRequest.getLevel());

        if (categoryRequest.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(categoryRequest.getParentCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id : " + categoryRequest.getParentCategoryId()));
            category.setParentCategory(parent);
        }
        return Mapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return Mapper.toResponse(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId)));
    }

    @Override
    public List<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent().stream().map(Mapper::toResponse).toList();
    }

    @Override
    public List<CategoryResponse> getCategoriesByLevel(Integer level, Pageable pageable) {
        return categoryRepository.findByLevel(level, pageable).getContent().stream().map(Mapper::toResponse).toList();
    }

    @Override
    public List<CategoryResponse> getSubCategories(Long parentCategoryId) {
        return categoryRepository.findByParentCategory_Id(parentCategoryId)
                .stream()
                .map(Mapper::toResponse)
                .toList();
    }

    @Override
    public List<CategoryResponse> getRootCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent().stream().filter(category -> category.getParentCategory() == null).map(Mapper::toResponse).toList();

    }
}
