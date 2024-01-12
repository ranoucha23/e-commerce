package com.test.ecommerce.controller;

import com.test.ecommerce.dto.CategoryDto;
import com.test.ecommerce.dto.ProductDto;
import com.test.ecommerce.service.CategoryService;
import com.test.ecommerce.service.ImageStorage;
import com.test.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static com.test.ecommerce.utils.Constants.PRODUCT_ENDPOINT;

@RestController
@RequestMapping(value = PRODUCT_ENDPOINT)
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;
    private CategoryService categoryService ;

    @Qualifier("ProductImageStorageImpl")
    private ImageStorage imageStorage;

    public ProductController(ProductService productService, CategoryService categoryService, ImageStorage imageStorage) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imageStorage = imageStorage;}

    @PostMapping(value = "/saveProduct",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        return productService.delete(id);
    }

    @GetMapping(value = "/getAllProducts",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/getProduct/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductsByCategoryId(@PathVariable Long categoryId) {
        ResponseEntity<CategoryDto> responseEntity  = categoryService.findById(categoryId);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return Collections.emptyList();
        }

        CategoryDto category = responseEntity.getBody();

        return productService.getProductsByCategory(category);
    }

    @PostMapping("/uploadProductImage/{id}")
    public ResponseEntity<ProductDto> uploadProductImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return this.productService.uploadImageProduct(id, image);
    }
    @GetMapping("/downloadProductImage/{imageName}")
    public ResponseEntity<Resource> downloadProductImage(@PathVariable String imageName, HttpServletRequest request) {
        return this.imageStorage.downloadProductImage(imageName, request);
    }
}
