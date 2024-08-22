package org.example.onlineshopping.service;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.entity.BasketItem;
import org.example.onlineshopping.repository.BasketItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketItemService {

    private final BasketItemRepository basketItemRepository;

    public void removeBasketItem(Long basketItemId) {
        Optional<BasketItem> basketItemOpt = basketItemRepository.findById(basketItemId);
        if (basketItemOpt.isPresent()) {
            basketItemRepository.delete(basketItemOpt.get());
        } else {
            throw new RuntimeException("Basket item not found");
        }
    }

}

