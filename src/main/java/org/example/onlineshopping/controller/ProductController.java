package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.request.ProductRequestDto;
import org.example.onlineshopping.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductRequestDto>> getAllProducts() {
        List<ProductRequestDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductRequestDto> createProduct(@RequestBody ProductRequestDto productDto) {
        ProductRequestDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRequestDto> getProductById(@PathVariable Long id) {
        ProductRequestDto product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductRequestDto>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductRequestDto> products = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductRequestDto>> getProductsByPriceRange(
            @RequestParam Long minPrice,
            @RequestParam Long maxPrice) {
        List<ProductRequestDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequestDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productDto) {
        ProductRequestDto updatedProduct = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
