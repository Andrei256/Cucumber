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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public String showProductPage(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute("productDescription", productDescriptionService.get(id));
        return "product_page";
    }

    //SHOP

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

        if (productDescriptionService.addProductDescription(productDescription, file)) {
            productService.addProductOffer(seller, cost, productDescriptionService.get(productDescription.getId()));
            return "redirect:/product";
        }
        return "redirect:/product/new";
    }

    @GetMapping("/add/{id}")
    public String showPageForAddProductOffer(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute("productDescription", productDescriptionService.get(id));
        return "new_offer";
    }

    @PostMapping("/add")
    public String addProductOffer(
            @AuthenticationPrincipal User seller,
            @RequestParam(name = "cost") float cost,
            @RequestParam(name = "id") long id) {

            if (productService.addProductOffer(seller, cost, productDescriptionService.get(id))) {
                return "redirect:/product/" + id;
            }
        return "redirect:/product/add/" + id;
    }


}
