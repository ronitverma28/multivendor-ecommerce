package com.ronit.ecommerce_multivendor.controller;

import com.ronit.ecommerce_multivendor.dto.request.CategoryRequest;
import com.ronit.ecommerce_multivendor.dto.response.CategoryResponse;
import com.ronit.ecommerce_multivendor.service.CategoryService;
import com.ronit.ecommerce_multivendor.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(ApiResponse.ok("Category Created successfully", categoryService.createCategory(categoryRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(ApiResponse.ok("Category Updated Successfully", categoryService.updateCategory(id, categoryRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.ok("Category Deleted Successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories(@PageableDefault(size = 10, page = 0)Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Categories fetched Successfully", categoryService.getAllCategories(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.ok("Category fetched successfully", categoryService.getCategoryById(id)));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesByLevel(@PathVariable Integer level, @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Categories by level fetched successfully", categoryService.getCategoriesByLevel(level, pageable)));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getSubCategories(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.ok("All Sub-Categories fetched successfully", categoryService.getSubCategories(id)));
    }

    @GetMapping("/roots")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRootCategories(@PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.ok("All Parent Categories fetched successfully", categoryService.getRootCategories(pageable)));
    }

}
