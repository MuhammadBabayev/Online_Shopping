package org.example.onlineshopping.dto.response;

import lombok.Data;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.entity.OrderItem;

@Data
public class OrderResponseDto {
    Long orderId;
    Long totalAmount;
    String userName;
    String productName;
    Long price;

    public OrderResponseDto(Order order, OrderItem orderItem) {
        this.orderId = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.userName = order.getUser().getUsername();
        this.productName = orderItem.getProduct().getName();
        this.price = orderItem.getProduct().getPrice();
    }
}
