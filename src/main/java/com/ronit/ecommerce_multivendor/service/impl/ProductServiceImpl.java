package com.ronit.ecommerce_multivendor.service.impl;

import com.ronit.ecommerce_multivendor.dto.request.ProductRequest;
import com.ronit.ecommerce_multivendor.dto.response.ProductResponse;
import com.ronit.ecommerce_multivendor.exception.BadRequestException;
import com.ronit.ecommerce_multivendor.exception.ResourceNotFoundException;
import com.ronit.ecommerce_multivendor.mapper.Mapper;
import com.ronit.ecommerce_multivendor.model.Category;
import com.ronit.ecommerce_multivendor.model.Product;
import com.ronit.ecommerce_multivendor.model.Seller;
import com.ronit.ecommerce_multivendor.repository.CategoryRepository;
import com.ronit.ecommerce_multivendor.repository.ProductRepository;
import com.ronit.ecommerce_multivendor.repository.SellerRepository;
import com.ronit.ecommerce_multivendor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(String email, ProductRequest productRequest) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with id : " + email));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + productRequest.getCategoryId()));

        Product product = Product.builder()
                .title(productRequest.getTitle())
                .description(productRequest.getDescription())
                .mrpPrice(productRequest.getMrpPrice())
                .sellingPrice(productRequest.getSellingPrice())
                .quantity(productRequest.getQuantity())
                .color(productRequest.getColor())
                .sizes(productRequest.getColor())
                .images(productRequest.getImages())
                .discountPercent(productRequest.getDiscountPercent())
                .numRating(productRequest.getNumRating())
                .category(category)
                .seller(seller)
                .build();

        return Mapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(String email, Long productId, ProductRequest productRequest) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with id : " + email));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + productRequest.getCategoryId()));

        if(!product.getSeller().equals(seller)) throw new BadRequestException("You don't have any product of id : " + productId);

        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setMrpPrice(productRequest.getMrpPrice());
        product.setImages(productRequest.getImages());
        product.setSellingPrice(productRequest.getSellingPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDiscountPercent(productRequest.getDiscountPercent());
        product.setColor(productRequest.getColor());
        product.setSizes(productRequest.getSizes());
        product.setNumRating(productRequest.getNumRating());
        product.setCategory(category);

        return Mapper.toResponse(productRepository.save(product));

    }

    @Override
    public void deleteProduct(String email, Long productId) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with id : " + email));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));

        if(!seller.equals(product.getSeller())) throw new BadCredentialsException("You cannot remove this product");

        productRepository.delete(product);

    }

    @Override
    public List<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent().stream().map(Mapper::toResponse).toList();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return Mapper.toResponse(productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found with id : " + productId)));
    }

    @Override
    public List<ProductResponse> searchProducts(String title, String description, String color, String sizes, Integer minPrice, Integer maxPrice, Pageable pageable) {
       return productRepository.searchProducts(title, description, color, sizes, minPrice, maxPrice, pageable).getContent().stream().map(Mapper::toResponse).toList();
    }

    @Override
    public List<ProductResponse> getMyProducts(String email, Pageable pageable) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with id : " + email));
        return productRepository.findAll(pageable).getContent().stream().filter((product)-> product.getSeller().equals(seller)).map(Mapper::toResponse).toList();
    }

    @Override
    public ProductResponse updateInventory(String email, Long productId, Integer quantity) {
        Seller seller = sellerRepository.findByUser_Email(email).orElseThrow(() -> new ResourceNotFoundException("Seller not found with id : " + email));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));

        if(!product.getSeller().equals(seller)) throw new BadCredentialsException("You can't updateSellerProfile the product with id : " + productId);

        product.setQuantity(quantity);
        return Mapper.toResponse(productRepository.save(product));
    }
}
