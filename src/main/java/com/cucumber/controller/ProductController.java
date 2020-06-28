package com.cucumber.controller;

import com.cucumber.model.Product;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showListProducts(Model model) {
        List<Product> listProducts = productService.getListUniqueProducts();
        model.addAttribute("listProducts", listProducts);
        return "list_products";
    }
}
