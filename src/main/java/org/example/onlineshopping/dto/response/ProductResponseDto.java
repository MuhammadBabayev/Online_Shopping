package org.example.onlineshopping.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    String name;
    String description;
    Long price;
    Long quantity;
}
