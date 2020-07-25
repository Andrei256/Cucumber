package com.cucumber.service;

import com.cucumber.model.ProductDescription;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductDescriptionService {
    void save(ProductDescription productDescription);
    List<ProductDescription> getAll();
    ProductDescription get(long id);
    void delete(long id);
    boolean addProductDescription(ProductDescription productDescription, MultipartFile file) throws IOException;
}
