package com.cucumber.controller;

import com.cucumber.model.Basket;
import com.cucumber.model.Order;
import com.cucumber.model.State;
import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasketService basketService;

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
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber
    ) {
        Basket basket = basketService.getBasket(buyer);
        if (orderService.addOrder(basket, phoneNumber, address)) {
            basketService.delete(basket.getId());
            return "redirect:/product";
        }
        return "redirect:/basket";
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
//        model.addAttribute("states", State.values());
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
