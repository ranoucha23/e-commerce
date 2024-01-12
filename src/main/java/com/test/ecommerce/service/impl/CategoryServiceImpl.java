package com.test.ecommerce.service.impl;

import com.test.ecommerce.dto.CategoryDto;
import com.test.ecommerce.entity.Category;
import com.test.ecommerce.exception.EntityNotFoundException;
import com.test.ecommerce.exception.ErrorCodes;
import com.test.ecommerce.exception.InvalidEntityException;
import com.test.ecommerce.exception.InvalidOperationException;
import com.test.ecommerce.repository.CategoryRepository;
import com.test.ecommerce.service.CategoryService;
import com.test.ecommerce.validation.CategoryValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    @Override
    public ResponseEntity<CategoryDto> save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidation.validate(categoryDto);

        if (!errors.isEmpty()) {
            log.error("category is not valid {}", categoryDto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return ResponseEntity.ok(CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(categoryDto)
                )
        ));
    }

    @Override
    public ResponseEntity<CategoryDto> findById(Long id) {
        if (id == null) {
            log.error("Category ID is null");
            throw new InvalidOperationException("Impossible de trouver une catégorie avec un ID NULL", ErrorCodes.CATEGORY_ID_IS_NULL);
        }
        Optional<Category> category = categoryRepository.findById(id);

        return ResponseEntity.ok(Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune catégorie avec l'ID = " + id + " n'a été trouvée dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
        ));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.ok(categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (id == null) {
            log.error("Category ID is null");
            throw new InvalidOperationException("Impossible de supprimer une catégorie  avec un ID NULL",ErrorCodes.CATEGORY_ID_IS_NULL);
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
