package com.test.ecommerce.handler;

import com.test.ecommerce.exception.ErrorCodes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    private Integer httpCode;
    private String message;
    private ErrorCodes code;
    private List<String> errors;
}
