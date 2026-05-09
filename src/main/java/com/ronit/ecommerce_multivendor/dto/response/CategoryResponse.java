package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String categoryId;

    private String name;

    private Integer level;

    private ParentCategoryResponse parentCategory;

    @Builder.Default
    private List<CategoryResponse> subCategories = new ArrayList<>();
}