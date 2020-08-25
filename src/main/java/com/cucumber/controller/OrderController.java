package com.cucumber.controller;

import com.cucumber.model.*;
import com.cucumber.service.BasketService;
import com.cucumber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private BasketService basketService;

    @Autowired
    public OrderController(OrderService orderService, BasketService basketService) {
        this.orderService = orderService;
        this.basketService = basketService;
    }

    //USER

    @GetMapping
    public String showOrderPage(
            @AuthenticationPrincipal User buyer,
            Model model) {
        model.addAttribute("basket", basketService.getBasket(buyer));
        model.addAttribute("order", new Order());
        return "user/order";
    }

    @PostMapping("/add")
    public String addOrder(
            @AuthenticationPrincipal User buyer,
            @Valid Order order,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("basket", basketService.getBasket(buyer));
            model.addAttribute("order", order);
            return "user/order";
        }
        Basket basket = basketService.getBasket(buyer);
        orderService.addOrder(basket, order);
        basketService.delete(basket.getId());
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String showBuyerOrders(
            @AuthenticationPrincipal User buyer,
            Model model) {
        model.addAttribute("listOrders", orderService.getAllBuyersOrders(buyer.getId()));
        return "user/buyer_orders";
    }

    //SHOP

    @GetMapping("/{state}")
    public String showSellerOrders(
            @AuthenticationPrincipal User seller,
            @PathVariable("state") String state,
            Model model) {
        model.addAttribute("listOrders", orderService.getSellerOrders(seller.getId(), State.valueOf(state.toUpperCase())));
        model.addAttribute("states", State.values());
        return "shop/seller_orders";
    }

    @PostMapping("/{orderId}/state")
    public String completeOrder(
            @PathVariable("orderId") long orderId,
            @RequestParam("state") String state
    ) {
        orderService.editOrderState(orderId, State.valueOf(state.toUpperCase()));
        return "redirect:/order/active";
    }
}
