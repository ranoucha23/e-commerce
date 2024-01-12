package com.test.ecommerce.dto;

import com.test.ecommerce.entity.Category;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto fromEntity(Category category)
    {
        if(category ==null)
        {
            return null;
        }

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto)
    {
        if(categoryDto==null)
        {
            return null ;
        }
        Category category = new Category();
        if(categoryDto.getId() != null)
        {
            category.setId(categoryDto.getId());
        }
        category.setName(categoryDto.getName());

        return category;
    }
}
