package com.cucumber.service.impl;

import com.cucumber.model.ProductDescription;
import com.cucumber.repository.ProductDescriptionRepository;
import com.cucumber.service.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void save(ProductDescription productDescription) {
        productDescriptionRepository.save(productDescription);
    }

    @Override
    public List<ProductDescription> getAll() {
        return productDescriptionRepository.findAll();
    }

    @Override
    public ProductDescription get(long id) {
        return productDescriptionRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        productDescriptionRepository.deleteById(id);
    }

    @Override
    public boolean addProductDescription(ProductDescription productDescription, MultipartFile file) throws IOException {
        if (productDescriptionRepository.findByModelName(productDescription.getModelName()) == null) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                productDescription.setFilename(resultFilename);
            }
            return true;
        }
        return false;
    }
}
