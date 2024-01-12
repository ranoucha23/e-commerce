package com.test.ecommerce.service.impl;

import com.test.ecommerce.dto.CategoryDto;
import com.test.ecommerce.dto.ProductDto;
import com.test.ecommerce.entity.Category;
import com.test.ecommerce.entity.Product;
import com.test.ecommerce.exception.EntityNotFoundException;
import com.test.ecommerce.exception.ErrorCodes;
import com.test.ecommerce.exception.InvalidEntityException;
import com.test.ecommerce.exception.InvalidOperationException;
import com.test.ecommerce.repository.ProductRepository;
import com.test.ecommerce.service.ImageStorage;
import com.test.ecommerce.service.ProductService;
import com.test.ecommerce.validation.ProductValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSeviceImpl implements ProductService {

    private ProductRepository productRepository;
    private ImageStorage imageStorage;

    public ProductSeviceImpl(ProductRepository productRepository, ImageStorage imageStorage) {
        this.productRepository = productRepository;
        this.imageStorage = imageStorage;}

    @Override
    public ResponseEntity<ProductDto> save(ProductDto productDto) {
        List<String> errors = ProductValidation.validate(productDto);

        if (!errors.isEmpty()) {
            log.error("product is not valid {}", productDto);
            throw new InvalidEntityException("Le produit n'est pas valide", ErrorCodes.PRODUCT_NOT_VALID, errors);
        }
        return ResponseEntity.ok(ProductDto.fromEntity(
                productRepository.save(
                        ProductDto.toEntity(productDto)
                )
        ));
    }

    @Override
    public ResponseEntity<ProductDto> findById(Long id) {
        if (id == null) {
            log.error("Product ID is null");
            throw new InvalidOperationException("Impossible de trouver un produit avec un ID NULL", ErrorCodes.PRODUCT_ID_IS_NULL);
        }
        Optional<Product> product = productRepository.findById(id);

        return ResponseEntity.ok(Optional.of(ProductDto.fromEntity(product.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun produit avec l'ID = " + id + " n'a été trouvé dans la BDD",
                        ErrorCodes.PRODUCT_NOT_FOUND)
        ));
    }

    @Override
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (id == null) {
            log.error("Product ID is null");
            throw new InvalidOperationException("Impossible de supprimer un produit  avec un ID NULL",ErrorCodes.PRODUCT_ID_IS_NULL);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto category) {
        Category cat = CategoryDto.toEntity(category);
        return productRepository.findByCategory(cat).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ProductDto> uploadImageProduct(Long productId, MultipartFile image) {
        ResponseEntity<ProductDto> productResponse = this.findById(productId);
        String imageName=imageStorage.store(image);
        String fileImageDownloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("ecommerce/v1/product/downloadProductImage/").path(imageName).toUriString();
        ProductDto prodcutDto = productResponse.getBody();
        if (prodcutDto!=null)
            prodcutDto.setImage(fileImageDownloadUrl);

        return this.save(prodcutDto);

    }
}
