package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.CategoryRequestDto;
import org.example.onlineshopping.entity.Category;
import org.example.onlineshopping.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDto categoryDto) {
        Category createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryRequestDto>> getAllCategories() {
        List<CategoryRequestDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequestDto> getCategoryById(@PathVariable Long id) {
        CategoryRequestDto category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryDto){
        Category updatedCategory = categoryService.updateCategory(id,categoryDto);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
