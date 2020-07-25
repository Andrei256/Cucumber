package com.cucumber.service;

import com.cucumber.model.Product;
import com.cucumber.model.ProductDescription;
import com.cucumber.model.User;

import java.util.List;

public interface ProductService {
    void save(Product product);
    List<Product> getAll();
    Product get(long id);
    void delete(long id);
    boolean addProductOffer(User seller, float cost, ProductDescription productDescription);
}
