package com.test.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadImgProductsDir;

    public String getUploadImgProductsDir() {
        return uploadImgProductsDir;
    }

    public void setUploadImgProductsDir(String uploadImgProductsDir) {
        this.uploadImgProductsDir = uploadImgProductsDir;
    }
}
