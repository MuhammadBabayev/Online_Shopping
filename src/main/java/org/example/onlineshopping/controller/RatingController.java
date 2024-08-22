package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.RatingDto;
import org.example.onlineshopping.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingDto> rateProduct(@RequestParam Long productId, @RequestParam Long userId, @RequestParam int ratingValue) {
        RatingDto ratingDto = ratingService.rateProduct(productId, userId, ratingValue);
        return new ResponseEntity<>(ratingDto, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        double averageRating = ratingService.getAverageRating(productId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}

