package org.example.onlineshopping.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.RatingDto;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.entity.Rating;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.repository.RatingRepository;
import org.example.onlineshopping.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public RatingDto rateProduct(Long productId, Long userId, int ratingValue) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Rating rating = Rating.builder()
                .product(product)
                .user(user)
                .rating(ratingValue)
                .build();

        Rating savedRating = ratingRepository.save(rating);
        return convertToDto(savedRating);
    }

    public double getAverageRating(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return product.getAverageRating();
    }

    private RatingDto convertToDto(Rating rating) {
        return RatingDto.builder()
                .productId(rating.getProduct().getId())
                .userId(rating.getUser().getId())
                .rating(rating.getRating())
                .build();
    }
}

