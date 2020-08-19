package com.cucumber.service.impl;

import com.cucumber.model.Offer;
import com.cucumber.model.Product;
import com.cucumber.repository.ProductRepository;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
    public List<Product> getAllWhereActiveIsFalse() {
        return productRepository.findByActiveIsFalse();
    }

    @Override
    public Map<Product, Float> getAllWhereActiveIsTrueAndMinCost() {
        Map<Product, Float> productsMap = new HashMap<>();
        for (Product product : productRepository.findByActiveIsTrue()) {
            if (!product.getOffers().isEmpty()) {
                float num;
                float min = product.getOffers().get(0).getCost();
                for (Offer offer : product.getOffers()) {
                    num = offer.getCost();
                    if (num < min) {
                        min = num;
                    }
                }
                productsMap.put(product, min);
            } else {
                productsMap.put(product, null);
            }
        }
        return productsMap;
    }

    @Override
    public boolean addProduct(Product product, MultipartFile file) throws IOException {
        if (productRepository.findByModelName(product.getModelName()) == null) {
            product.setFilename(addImage(file));
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public void editProduct(long id, Product product, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Product productFromDB = productRepository.findById(id).get();
            product.setFilename(productFromDB.getFilename());
        } else {
            product.setFilename(addImage(file));
        }
        if (!product.isActive()) {
            product.setActive(true);
        }
        product.setId(id);
        productRepository.save(product);
    }

    private String addImage(MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            return resultFilename;
        }
        return null;
    }
}
