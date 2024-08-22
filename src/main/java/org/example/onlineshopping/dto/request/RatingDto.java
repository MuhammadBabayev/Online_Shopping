package org.example.onlineshopping.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDto {
    Long productId;
    Long userId;
    int rating;
}

