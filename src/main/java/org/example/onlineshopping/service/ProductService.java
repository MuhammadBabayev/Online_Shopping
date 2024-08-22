package org.example.onlineshopping.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.ProductRequestDto;
import org.example.onlineshopping.entity.Category;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.repository.CategoryRepository;
import org.example.onlineshopping.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CommentService commentService;

    public List<ProductRequestDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public ProductRequestDto createProduct(ProductRequestDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);

        return entityToDto(savedProduct);
    }

    public ProductRequestDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return entityToDto(product);
    }

    public List<ProductRequestDto> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ProductRequestDto> getProductsByPriceRange(Long minPrice, Long maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public ProductRequestDto updateProduct(Long productId, ProductRequestDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return entityToDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    private ProductRequestDto entityToDto(Product product) {
        return ProductRequestDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryId(product.getCategory().getId())
                .averageRating(product.getAverageRating())
                .comments(commentService.getCommentsByProductId(product.getId()))
                .build();
    }
}
