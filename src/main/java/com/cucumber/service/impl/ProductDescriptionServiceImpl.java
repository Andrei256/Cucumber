package com.cucumber.service.impl;

import com.cucumber.model.ProductDescription;
import com.cucumber.repository.ProductDescriptionRepository;
import com.cucumber.service.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;

    @Override
    public void save(ProductDescription productDescription) {
        productDescriptionRepository.save(productDescription);
    }

    @Override
    public List<ProductDescription> getAll() {
        return productDescriptionRepository.findAll();
    }

    @Override
    public ProductDescription get(Long id) {
        return productDescriptionRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        productDescriptionRepository.deleteById(id);
    }

    @Override
    public ProductDescription getProductDescriptionByModelName(String modelName) {
        return productDescriptionRepository.findByModelName(modelName);
    }
}
