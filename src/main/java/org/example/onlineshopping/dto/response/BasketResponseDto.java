package org.example.onlineshopping.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.onlineshopping.entity.Basket;
import org.example.onlineshopping.entity.BasketItem;

@Data
public class BasketResponseDto {
    Long id;
    String userName;
    String productName;
    Long quantity;
    Long price;

    public BasketResponseDto(Basket basket, BasketItem basketItem) {
        this.id = basket.getId();
        this.userName = basket.getUser().getUsername();
        this.productName = basketItem.getProduct().getName();
        this.quantity = basketItem.getQuantity();
        this.price = basketItem.getProduct().getPrice();
    }
}
