package org.example.onlineshopping.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.CommentDto;
import org.example.onlineshopping.entity.Comment;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.repository.CommentRepository;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CommentDto addComment(Long productId, Long userId, String content) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .product(product)
                .user(user)
                .content(content)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return entityToDto(savedComment);
    }

    public List<CommentDto> getCommentsByProductId(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private CommentDto entityToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .productId(comment.getProduct().getId())
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .build();
    }
}

