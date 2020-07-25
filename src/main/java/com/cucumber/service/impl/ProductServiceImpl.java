package com.cucumber.service.impl;

import com.cucumber.model.Product;
import com.cucumber.model.ProductDescription;
import com.cucumber.model.User;
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
    public Product get(long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean addProductOffer(User seller, float cost, ProductDescription productDescription) {
        if (productRepository.findBySeller_IdAndProductDescription_Id(seller.getId(), productDescription.getId()) == null) {
            Product product = new Product();
            product.setCost(cost);
            product.setProductDescription(productDescription);
            product.setSeller(seller);
            productRepository.save(product);
            return true;
        }
        return false;
    }

}
