package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.response.OrderResponseDto;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/user/{userId}/place-order")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderResponseDto> orderResponseDtos = orderService.getOrdersByUserId(userId);
            return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<Order>> getOrdersByUserIdAndDateRange(@PathVariable Long userId,
                                                                     @RequestParam LocalDateTime startDate,
                                                                     @RequestParam LocalDateTime endDate) {
        try {
            List<Order> orders = orderService.getOrdersByUserIdAndDateRange(userId, startDate, endDate);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{orderId}/apply-promo")
    public ResponseEntity<List<OrderResponseDto>> applyPromoCode(@PathVariable Long orderId,
                                                                 @RequestParam String promoCode) {
        try {
            List<OrderResponseDto> orderResponseDtos = orderService.applyPromoCode(orderId, promoCode);
            return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}