package com.ronit.ecommerce_multivendor.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer mrpPrice;

    @Column(nullable = false)
    private Integer sellingPrice;

    private Integer discountPercent;

    @Column(nullable = false)
    private Integer quantity;

    private String color;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    private Integer numRating;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Seller seller;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private String sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}
