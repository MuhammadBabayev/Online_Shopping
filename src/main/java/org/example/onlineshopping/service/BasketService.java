package org.example.onlineshopping.service;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.response.BasketResponseDto;
import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.entity.BasketItem;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.repository.BasketItemRepository;
import org.example.onlineshopping.repository.BasketRepository;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;

    public void addProductToBasket(Long userId, Long productId, Long quantity) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOpt.get();

        if (quantity > product.getQuantity()) {
            throw new RuntimeException("Insufficient product quantity available");
        }

        Basket basket = basketRepository.findByUser(user)
                .orElseGet(() -> createNewBasket(user));

        BasketItem basketItem = BasketItem.builder()
                .basket(basket)
                .product(product)
                .quantity(quantity)
                .build();

        basketItemRepository.save(basketItem);

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    private Basket createNewBasket(User user) {
        Basket basket = Basket.builder()
                .user(user)
                .build();
        return basketRepository.save(basket);
    }

    public List<BasketResponseDto> getBasketResponseByUserId(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        List<BasketResponseDto> basketResponseDtos = basket.getBasketItem().stream()
                .map(basketItem -> new BasketResponseDto(basket, basketItem))
                .collect(Collectors.toList());

        return basketResponseDtos;
    }

}
