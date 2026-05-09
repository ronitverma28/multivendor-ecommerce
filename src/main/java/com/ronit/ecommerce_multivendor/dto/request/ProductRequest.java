package com.ronit.ecommerce_multivendor.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "MRP is required")
    @Min(value = 1, message = "MRP must be positive ")
    private Integer mrpPrice;

    @NotNull(message = "Selling price is required")
    @Min(value = 1, message = "Selling price must be positive")
    private Integer sellingPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private String color;
    private String sizes;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @Builder.Default
    private List<String> images = new ArrayList<>();
}
