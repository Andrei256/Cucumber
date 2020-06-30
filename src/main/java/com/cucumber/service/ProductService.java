package com.cucumber.service;

import com.cucumber.model.Product;

import java.util.List;

public interface ProductService {
    void save(Product product);
    List<Product> getAll();
    Product get(Long id);
    void delete(Long id);
}
