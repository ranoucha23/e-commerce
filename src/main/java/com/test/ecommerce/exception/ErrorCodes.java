package com.test.ecommerce.exception;

public enum ErrorCodes {

    CATEGORY_NOT_FOUND(1000),
    CATEGORY_NOT_VALID(1001),
    CATEGORY_ID_IS_NULL(1002),

    PRODUCT_NOT_FOUND(2000),
    PRODUCT_NOT_VALID(2001),
    PRODUCT_ID_IS_NULL(2002)
    ;


    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
