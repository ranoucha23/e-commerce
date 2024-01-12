package com.test.ecommerce.validation;

import com.test.ecommerce.dto.ProductDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductValidation {

    public static List<String> validate(ProductDto productDto)
    {

        List<String> errors = new ArrayList<>() ;

        if(productDto== null)
        {

            errors.add("Veuillez renseigner le nom du produit");
            errors.add("Veuillez renseigner le prix du produit");
            errors.add("Veuillez renseigner la catégorie du produit");

        }

        else {

            if(!StringUtils.hasLength(productDto.getName()))
            {
                errors.add("Veuillez renseigner le nom du produit");
            }

            if(productDto.getPrice() == 0)
            {
                errors.add("Veuillez renseigner le prix du produit");
            }

            if(productDto.getCategory() == null)
            {
                errors.add("Veuillez renseigner la catégorie du produit");
            }

        }

        return errors ;
    }
}
