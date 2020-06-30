package com.cucumber.service;

import com.cucumber.model.ProductDescription;

import java.util.List;

public interface ProductDescriptionService {
    void save(ProductDescription productDescription);
    List<ProductDescription> getAll();
    ProductDescription get(Long id);
    void delete(Long id);
    ProductDescription getProductDescriptionByModelName(String modelName);
}
