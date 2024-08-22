package org.example.onlineshopping.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    Long price;
    Long quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Comment> comments;

    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }
}
