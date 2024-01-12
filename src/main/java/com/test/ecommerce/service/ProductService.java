package com.test.ecommerce.service;

import com.test.ecommerce.dto.CategoryDto;
import com.test.ecommerce.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ResponseEntity<ProductDto> save(ProductDto productDto);

    ResponseEntity<ProductDto> findById(Long id);

    ResponseEntity<List<ProductDto>> findAll();

    ResponseEntity<Void> delete(Long id);

    List<ProductDto> getProductsByCategory(CategoryDto category);

    ResponseEntity<ProductDto> uploadImageProduct(Long productId, MultipartFile image);
}
