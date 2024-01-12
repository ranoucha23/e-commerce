package com.test.ecommerce.validation;

import com.test.ecommerce.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidation {

    public static List<String> validate(CategoryDto categoryDto)
    {

        List<String> errors = new ArrayList<>() ;

        if(categoryDto== null)
        {

            errors.add("Veuillez renseigner le nom du catégorie");

        }

        else {

            if(!StringUtils.hasLength(categoryDto.getName()))
            {
                errors.add("Veuillez renseigner le nom du catégorie");
            }

        }

        return errors ;
    }
}
