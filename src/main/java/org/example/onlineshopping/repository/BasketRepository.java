package org.example.onlineshopping.repository;

import org.example.onlineshopping.dto.response.BasketResponseDto;
import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    Optional<Basket> findByUserId(Long userId);

    Optional<Basket> findByUser(User user);
}
