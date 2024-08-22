package org.example.onlineshopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "basket_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long quantity;

    @ManyToOne
    Product product;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    Basket basket;
}
