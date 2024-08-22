package org.example.onlineshopping.dto.request;

import lombok.Builder;
import lombok.Data;
import org.example.onlineshopping.entity.Product;

import java.util.List;

@Data
@Builder
public class ProductRequestDto {
     String name;
     String description;
     Long price;
     Long quantity;
     Long categoryId;
     double averageRating;
     List<CommentDto> comments;

}
