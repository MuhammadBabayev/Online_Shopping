package org.example.onlineshopping.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    private Long productId;
    private Long userId;
    private String content;
}
