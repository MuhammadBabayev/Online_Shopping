package org.example.onlineshopping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.response.OrderResponseDto;
import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.entity.OrderItem;
import org.example.onlineshopping.entity.PromoCode;
import org.example.onlineshopping.repository.BasketRepository;
import org.example.onlineshopping.repository.OrderItemRepository;
import org.example.onlineshopping.repository.OrderRepository;
import org.example.onlineshopping.repository.PromoCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final OrderItemRepository orderItemRepository;

    public Order placeOrder(Long userId) {
        Basket basket = basketRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        Long totalAmount = basket.getBasketItem().stream()
                .mapToLong(basketItem -> basketItem.getQuantity() * basketItem.getProduct().getPrice())
                .sum();

        Order order = Order.builder()
                .user(basket.getUser())
                .totalAmount(totalAmount)
                .createdDate(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = basket.getBasketItem().stream()
                .map(basketItem -> OrderItem.builder()
                        .order(order)
                        .product(basketItem.getProduct())
                        .quentity(basketItem.getQuantity())
                        .price(basketItem.getProduct().getPrice())
                        .build())
                .toList();

        order.setOrderItem(orderItems);
        return orderRepository.save(order);
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .flatMap(order -> order.getOrderItem().stream()
                        .map(orderItem -> new OrderResponseDto(order, orderItem)))
                .collect(Collectors.toList());
    }

    public List<Order> getOrdersByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findAllByUserIdAndCreatedDateBetween(userId, startDate, endDate);
    }

    public List<OrderResponseDto> applyPromoCode(Long orderId, String promoCode) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        PromoCode promo = promoCodeRepository.findByCode(promoCode)
                .orElseThrow(() -> new RuntimeException("Promo code not found"));

        if (!promo.getIsActive()) {
            throw new RuntimeException("Promo code is inactive");
        }

        double discount = promo.getDiscountPercentage();
        double totalAmount = order.getTotalAmount();
        double discountedAmount = totalAmount - (totalAmount * discount / 100);
        order.setTotalAmount((long) discountedAmount);

        orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        List<OrderResponseDto> orderResponseDtos = orderItems.stream()
                .map(orderItem -> new OrderResponseDto(order, orderItem))
                .collect(Collectors.toList());

        return orderResponseDtos;
    }
}
