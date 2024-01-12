package com.test.ecommerce.controller;

import com.test.ecommerce.dto.CategoryDto;
import com.test.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.test.ecommerce.utils.Constants.CATEGORY_ENDPOINT;

@RestController
@RequestMapping(value = CATEGORY_ENDPOINT)
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @PostMapping(value = "/saveCategory",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        return categoryService.delete(id);
    }

    @GetMapping(value = "/getAllCategories",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> findAll() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/getCategory/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> findById(@PathVariable(value = "id") Long id) {
        return categoryService.findById(id);
    }
}
