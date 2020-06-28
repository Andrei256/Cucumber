package com.cucumber.service.impl;

import com.cucumber.model.Product;
import com.cucumber.repository.ProductRepository;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getListUniqueProducts() {
        return productRepository.getListUniqueProductsFromDB();
    }
}
