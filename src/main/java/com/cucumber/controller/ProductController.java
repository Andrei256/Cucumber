package com.cucumber.controller;

import com.cucumber.model.Category;
import com.cucumber.model.Product;
import com.cucumber.model.ProductDescription;
import com.cucumber.model.User;
import com.cucumber.service.ProductDescriptionService;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @Autowired
    private ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String showListProductsPage(Model model) {
        List<ProductDescription> productDescriptions = productDescriptionService.getAll();
        Map<ProductDescription, Float> productDescriptionsMap = new HashMap<>();
        for (ProductDescription productDescription : productDescriptions) {
            float num;
            float min = productDescription.getProducts().get(0).getCost();
            for (Product product : productDescription.getProducts()) {
                num = product.getCost();
                if (num < min) {
                    min = num;
                }
            }
            productDescriptionsMap.put(productDescription, min);

        }
        model.addAttribute("productDescriptionsMap", productDescriptionsMap);
        return "list_products";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        ProductDescription productDescription = new ProductDescription();
        model.addAttribute("productDescription", productDescription);
        model.addAttribute("categories", Category.values());
        return "new_product";
    }

    @PostMapping("/save")
    public String saveNewProduct(
            @AuthenticationPrincipal User seller,
            @ModelAttribute("product") ProductDescription productDescription,
            @RequestParam("cost") float cost,
            @RequestParam("file") MultipartFile file
            ) throws IOException {

        if (productDescriptionService.getProductDescriptionByModelName(productDescription.getModelName()) == null) {
            Product product = new Product();
            product.setCost(cost);

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

            product.setProductDescription(productDescription);
            product.setSeller(seller);
            productDescriptionService.save(productDescription);
            productService.save(product);
        }
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String showProductPage(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute("productDescription", productDescriptionService.get(id));
        return "product_page";
    }
}
