package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.service.BasketItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/basket-items")
@RequiredArgsConstructor
public class BasketItemController {

    private final BasketItemService basketItemService;

    @DeleteMapping("/{basketItemId}")
    public ResponseEntity<String> removeBasketItem(@PathVariable Long basketItemId) {
        try {
            basketItemService.removeBasketItem(basketItemId);
            return new ResponseEntity<>("Basket item removed", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
