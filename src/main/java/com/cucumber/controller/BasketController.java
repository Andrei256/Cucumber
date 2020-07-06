package com.cucumber.controller;

import com.cucumber.model.Basket;
import com.cucumber.model.Product;
import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.ProductService;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showBasketPage(@AuthenticationPrincipal User buyer) {
        Basket basket = basketService.getBasketByBuyerId(buyer.getId());
        List<Product> list = basket.getProducts();
        for (Product p : list) {
            System.out.println(p.getCost());
        }
        return "basket";
    }

    @GetMapping("/add/{id}")
    public String addToBasket(@AuthenticationPrincipal User buyer,
                              @PathVariable("id") long id
    ) {

        Basket basket = basketService.getBasketByBuyerId(buyer.getId());
        if (basket == null) {
            basket = new Basket();
            basket.setBuyer(buyer);
        }
        List<Product> products = basket.getProducts();
        basket.getProducts().add(productService.get(id));
        basket.setProducts(products);
        basketService.save(basket);

        return "redirect:/product";
    }

}
