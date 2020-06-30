package com.cucumber.controller;

import com.cucumber.model.Category;
import com.cucumber.model.Product;
import com.cucumber.model.ProductDescription;
import com.cucumber.model.User;
import com.cucumber.service.ProductDescriptionService;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showListProducts(Model model) {
        List<ProductDescription> productDescriptions = productDescriptionService.getAll();
        model.addAttribute("productDescriptions", productDescriptions);
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
            @ModelAttribute("cost") float cost
//            @RequestParam("category") Category category
            ) {
        if (productDescriptionService.getProductDescriptionByModelName(productDescription.getModelName()) == null) {
            System.out.println("Hello");
            Product product = new Product();
            product.setCost(cost);
//            List<User> sellers = new ArrayList<>();
            Set<User> sellers = new HashSet<>();
            sellers.add(seller);
//            productDescription.setCategory(category);
            product.setProductDescription(productDescription);
            productDescriptionService.save(productDescription);
            product.setSellers(sellers);
            productService.save(product);
        }
        return "redirect:/product";
    }
}
