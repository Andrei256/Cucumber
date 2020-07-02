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

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showListProductsPage(Model model) {
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
            ) {
        if (productDescriptionService.getProductDescriptionByModelName(productDescription.getModelName()) == null) {
            Product product = new Product();
            product.setCost(cost);
            product.setProductDescription(productDescription);
            product.setSeller(seller);
            productDescriptionService.save(productDescription);
            productService.save(product);
        }
        return "redirect:/product";
    }
    @GetMapping("/{id}")
    public String showProductPage(@PathVariable(name = "id") long id, Model model) {
        ProductDescription productDescription = productDescriptionService.get(id);
        model.addAttribute("productDescription", productDescription);
        return "product_page";
    }
}
