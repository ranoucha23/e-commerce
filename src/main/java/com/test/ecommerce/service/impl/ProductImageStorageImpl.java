package com.test.ecommerce.service.impl;

import com.test.ecommerce.config.FileStorageProperties;
import com.test.ecommerce.exception.FileStorageException;
import com.test.ecommerce.service.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;


@Service("ProductImageStorageImpl")
public class ProductImageStorageImpl implements ImageStorage {
    private final Path imageLocation;

    @Autowired
    public ProductImageStorageImpl(FileStorageProperties fileStorageProperties) {
        this.imageLocation= Paths.get(fileStorageProperties.getUploadImgProductsDir()).toAbsolutePath().normalize();

    }


    @Override
    public String store(MultipartFile file) {
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")){
                throw new FileStorageException("File name contains invalid path sequence "+fileName);
            }
            Files.copy(file.getInputStream(),this.imageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new RuntimeException("Fail",e);
        }
        return file.getOriginalFilename();
    }

    @Override
    public Resource loadResource(String filename) {
        try {
            Path path = imageLocation.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("Fail");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Fail");
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(imageLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(imageLocation);
        }catch (IOException e){
            throw new RuntimeException("Could not initialize storage");
        }
    }

    @Override
    public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.imageLocation,1).filter(item->!item.equals(this.imageLocation)).map(this.imageLocation::relativize);
        }catch (IOException e){
            throw new RuntimeException("Failed to read stored images");
        }
    }

    @Override
    public ResponseEntity<Resource> downloadProductImage(String imageName, HttpServletRequest request) {

        Resource resource = this.loadResource(imageName);
        String contentType = null;
        try {
            if (resource!=null){
                contentType=request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        assert contentType != null;
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }

}
