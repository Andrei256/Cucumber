package com.cucumber.controller;

import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.ProductService;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showBasketPage(@AuthenticationPrincipal User buyer, Model model) {
        model.addAttribute("basket", basketService.getBasketByBuyerId(buyer.getId()));
        return "basket";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductFromBasket(@AuthenticationPrincipal User buyer, @PathVariable("id") long id) {
        basketService.deleteProductFromBasket(productService.get(id), buyer);
        return "redirect:/basket";
    }

    @GetMapping("/add/{id}")
    public String addProductInBasket(@AuthenticationPrincipal User buyer,
                                     @PathVariable("id") long id
    ) {
        basketService.addProductInBasket(productService.get(id), buyer);
        return "redirect:/product";
    }

}
