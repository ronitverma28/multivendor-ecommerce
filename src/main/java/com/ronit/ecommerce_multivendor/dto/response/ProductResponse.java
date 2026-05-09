package com.ronit.ecommerce_multivendor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private String description;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Integer discountPercent;
    private Integer quantity;
    private String color;
    private String sizes;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    private Integer numRating;
    private ParentCategoryResponse category;
    private SellerSummary seller;
    private LocalDateTime createdAt;
}
