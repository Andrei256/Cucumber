package com.cucumber.controller;

import com.cucumber.model.Category;
import com.cucumber.model.Offer;
import com.cucumber.model.Product;
import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OfferService;
import com.cucumber.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private OfferService offerService;
    private BasketService basketService;

    @Autowired
    public ProductController(ProductService productService, OfferService offerService, BasketService basketService) {
        this.productService = productService;
        this.offerService = offerService;
        this.basketService = basketService;
    }

    @GetMapping
    public String showListProductsPage(Model model) {
        model.addAttribute("productsMap", productService.getAllWhereActiveIsTrueAndMinCost());
        return "list_products";
    }

    @GetMapping("/category/{category}")
    public String showListProductsByCategory(
            Model model,
            @PathVariable("category") String category
            ) {
        model.addAttribute("productsMap", productService.getAllWhereActiveIsTrueAndCategoryAndMinCost(Category.valueOf(category.toUpperCase())));
        return "list_products";
    }

    @GetMapping("/{productId}")
    public String showProductPage(
            @AuthenticationPrincipal User buyer,
            @PathVariable("productId") long productId,
            Model model) {
        model.addAttribute("product", productService.get(productId));
        model.addAttribute("basket", basketService.getBasket(buyer));
        return "product_page";
    }

    @PostMapping("/search")
    public String search(
            @RequestParam String keyword,
            Model model) {
        model.addAttribute("productsMap", productService.search(keyword));
        return "product_search";
    }

    //SHOP

    @PreAuthorize("hasAuthority('SHOP')")
    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", Category.values());
        return "shop/new_product";
    }

    @PreAuthorize("hasAuthority('SHOP')")
    @PostMapping("/add")
    public String addNewProduct(
            @RequestParam("file") MultipartFile file,
            @Valid Product product,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("product", product);
            model.addAttribute("categories", Category.values());
            return "shop/new_product";
        }
        productService.addProduct(product, file);
        return "redirect:/product";
    }

    @PreAuthorize("hasAuthority('SHOP')")
    @GetMapping("/{productId}/offer/new")
    public String showPageForAddProductOffer(
            @AuthenticationPrincipal User seller,
            @PathVariable(name = "productId") long productId,
            Model model) {
        model.addAttribute("offer", offerService.getBySellerIdAndProductId(seller.getId(), productId));
        model.addAttribute("product", productService.get(productId));
        return "shop/new_offer";
    }

    @PreAuthorize("hasAuthority('SHOP')")
    @PostMapping("/{productId}/offer/add")
    public String addProductOffer(
            @AuthenticationPrincipal User seller,
            @PathVariable(name = "productId") long productId,
            @Valid Offer offer,
            BindingResult bindingResult,
            Model model
            ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("offer", offer);
            model.addAttribute("product", productService.get(productId));
            return "shop/new_offer";
        }
        offerService.addOffer(seller, offer, productService.get(productId));
        return "redirect:/product/" + productId;
    }

    //ADMIN

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{productId}/edit")
    public String showEditProductPage(
            @PathVariable("productId") long productId,
            Model model) {
        model.addAttribute("product", productService.get(productId));
        model.addAttribute("categories", Category.values());
        return "admin/edit_product";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/edit")
    public String editProduct(
            @PathVariable("productId") long productId,
            @ModelAttribute("product") Product product,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        productService.editProduct(productId, product, file);
        return "redirect:/product/fromSellers/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/delete")
    public String deleteProductFromOffersList(@PathVariable("productId") long productId) {
        productService.delete(productId);
        return "redirect:/product/fromSellers/all";
    }
}
