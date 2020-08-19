package com.cucumber.controller;

import com.cucumber.model.Category;
import com.cucumber.model.Product;
import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OfferService;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private BasketService basketService;

    @GetMapping
    public String showListProductsPage(Model model) {
        model.addAttribute("productsMap", productService.getAllWhereActiveIsTrueAndMinCost());
        return "list_products";
    }

    @GetMapping("/{productId}")
    public String showProductPage(
            @AuthenticationPrincipal User buyer,
            @PathVariable(name = "productId") long productId,
            Model model) {
        model.addAttribute("product", productService.get(productId));
        model.addAttribute("basket", basketService.getBasket(buyer));
        return "product_page";
    }

    //SHOP

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", Category.values());
        return "shop/new_product";
    }

    @PostMapping("/add")
    public String addNewProduct(
            @ModelAttribute("product") Product product,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (productService.addProduct(product, file)) {
            return "redirect:/product";
        }
        return "redirect:/product/new";
    }

    @GetMapping("/{productId}/offer/new")
    public String showPageForAddProductOffer(
            @PathVariable(name = "productId") long productId,
            Model model) {
        model.addAttribute("product", productService.get(productId));
        return "shop/new_offer";
    }

    @PostMapping("/{productId}/offer/add")
    public String addProductOffer(
            @AuthenticationPrincipal User seller,
            @RequestParam(name = "cost") float cost,
            @PathVariable(name = "productId") long productId) {
        offerService.addOffer(seller, cost, productService.get(productId));
        return "redirect:/product/" + productId;
    }

    //ADMIN

    @GetMapping("/fromSellers/{sort}")
    public String showPageOffersFromSellers(
            @PathVariable("sort") String sort,
            Model model) {
        switch (sort) {
            case "new":
                model.addAttribute("productsList", productService.getAllWhereActiveIsFalse());
                break;
            case "all":
                model.addAttribute("productsList", productService.getAll());
                break;
        }
        return "admin/products_from_sellers";
    }

    @GetMapping("/{productId}/edit")
    public String showEditProductPage(
            @PathVariable("productId") long productId,
            Model model) {
        model.addAttribute("product", productService.get(productId));
        model.addAttribute("categories", Category.values());
        return "admin/edit_product";
    }

    @PostMapping("/{productId}/edit")
    public String editProduct(
            @PathVariable("productId") long productId,
            @ModelAttribute("product") Product product,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        productService.editProduct(productId, product, file);
        return "redirect:/product/fromSellers/all";
    }

    @PostMapping("/{productId}/delete")
    public String deleteProductFromBasket(@PathVariable("productId") long productId) {
        productService.delete(productId);
        return "redirect:/product/fromSellers/all";
    }
}
