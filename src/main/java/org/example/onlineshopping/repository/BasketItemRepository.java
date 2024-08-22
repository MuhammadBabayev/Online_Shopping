package org.example.onlineshopping.repository;

import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.entity.BasketItem;
import org.example.onlineshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    Optional<BasketItem> findByBasketAndProduct(Basket basket, Product product);

    List<BasketItem> findByBasketId(Long basketId);
}
