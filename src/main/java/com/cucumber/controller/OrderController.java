package com.cucumber.controller;

import com.cucumber.model.Order;
import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasketService basketService;

    @GetMapping
    public String showOrderPage(@AuthenticationPrincipal User buyer, Model model) {
        model.addAttribute("basket", basketService.getBasketByBuyerId(buyer.getId()));
        model.addAttribute("order", new Order());
        return "order";
    }
    @PostMapping
    public String addOrder(@AuthenticationPrincipal User buyer) {


        return "";
    }
}
