package com.cucumber.controller;

import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private BasketService basketService;

    @GetMapping
    public String showBasketPage() {
        return "user/basket";
    }

    @PostMapping("/{basketId}/offer/{offerId}/delete")
    public String deleteProductFromBasket(
            @PathVariable("basketId") long basketId,
            @PathVariable("offerId") long offerId) {
        basketService.deleteProductFromBasket(offerService.get(offerId), basketId);
        return "redirect:/basket";
    }

    @PostMapping("/{basketId}/add")
    public String addProductInBasket(
            @PathVariable("basketId") long basketId,
            @RequestParam("offerId") long offerId
    ) {
        basketService.addProductInBasket(offerService.get(offerId), basketId);
        return "redirect:/product";
    }

    @ModelAttribute
    public void getBasket(
            Model model,
            @AuthenticationPrincipal User buyer) {
        model.addAttribute("basket", basketService.getBasket(buyer));
    }
}
