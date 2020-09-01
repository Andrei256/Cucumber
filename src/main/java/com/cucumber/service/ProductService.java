package com.cucumber.service;

import com.cucumber.model.Category;
import com.cucumber.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    void save(Product product);

    List<Product> getAll();

    Product get(long id);

    void delete(long id);

    List<Product> getAllWhereActiveIsFalse();

    Map<Product, Integer> getAllWhereActiveIsTrueAndMinCost();

    void addProduct(Product product, MultipartFile file) throws IOException;

    void editProduct(long id, Product product, MultipartFile file) throws IOException;

    Map<Product, Integer> search(String keyword);

    Map<Product, Integer> getAllWhereActiveIsTrueAndCategoryAndMinCost(Category category);
}
