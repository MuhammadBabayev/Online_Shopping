package org.example.onlineshopping.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.CategoryRequestDto;
import org.example.onlineshopping.entity.Category;
import org.example.onlineshopping.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }

    public List<CategoryRequestDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public CategoryRequestDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return entityToDto(category);
    }

    public Category updateCategory(Long id,CategoryRequestDto categoryDto){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            Category existingCategory = category.get();
            existingCategory.setName(categoryDto.getName());
            existingCategory.setDescription(categoryDto.getDescription());
            categoryRepository.save(existingCategory);
            return existingCategory;
        }else{
            return null;
        }
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }


    private CategoryRequestDto entityToDto(Category category) {
        CategoryRequestDto categoryDto = new CategoryRequestDto();
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }}
