package org.example.onlineshopping.repository;

import org.example.onlineshopping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);
    List<Order> findAllByUserIdAndCreatedDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
