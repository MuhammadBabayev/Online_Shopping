package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.response.BasketResponseDto;
import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductToBasket(@RequestParam Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Long quantity) {
        try {
            basketService.addProductToBasket(userId, productId, quantity);
            return ResponseEntity.ok("Product added to basket successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BasketResponseDto>> getBasketByUserId(@PathVariable Long userId) {
        try {
            List<BasketResponseDto> basketResponseDtos = basketService.getBasketResponseByUserId(userId);
            return new ResponseEntity<>(basketResponseDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
