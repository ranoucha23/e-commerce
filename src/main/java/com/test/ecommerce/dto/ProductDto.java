package com.test.ecommerce.dto;

import com.test.ecommerce.entity.Product;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private String image;
    private CategoryDto category;

    public static ProductDto fromEntity(Product product)
    {
        if(product ==null)
        {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .category(CategoryDto.fromEntity(product.getCategory()))
                .build();
    }

    public static Product toEntity(ProductDto productDto)
    {
        if(productDto==null)
        {
            return null ;
        }
        Product product = new Product();
        if(productDto.getId() != null)
        {
            product.setId(productDto.getId());
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setCategory(CategoryDto.toEntity(productDto.getCategory()));

        return product;
    }
}
