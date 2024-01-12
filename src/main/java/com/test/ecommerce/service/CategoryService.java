package com.test.ecommerce.service;

import com.test.ecommerce.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<CategoryDto> save(CategoryDto categoryDto);

    ResponseEntity<CategoryDto> findById(Long id);

    ResponseEntity<List<CategoryDto>> findAll();

    ResponseEntity<Void> delete(Long id);
}
