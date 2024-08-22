package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.CommentDto;
import org.example.onlineshopping.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestParam Long productId,
                                                 @RequestParam Long userId,
                                                 @RequestParam String content) {
        CommentDto commentDto = commentService.addComment(productId, userId, content);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDto>> getCommentsByProductId(@PathVariable Long productId) {
        List<CommentDto> comments = commentService.getCommentsByProductId(productId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}

