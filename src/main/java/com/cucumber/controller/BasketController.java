package com.cucumber.controller;

import com.cucumber.model.User;
import com.cucumber.service.BasketService;
import com.cucumber.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAuthority('USER')")
@Controller
@RequestMapping("/basket")
public class BasketController {

    private OfferService offerService;
    private BasketService basketService;

    @Autowired
    public BasketController(OfferService offerService, BasketService basketService) {
        this.offerService = offerService;
        this.basketService = basketService;
    }

    //USER

    @GetMapping
    public String showBasketPage() {
        return "user/basket";
    }

    @PostMapping("/{basketId}/offer/{offerId}/delete")
    public String deleteProductFromBasket(
            @PathVariable("basketId") long basketId,
            @PathVariable("offerId") long offerId) {
        basketService.deleteOfferFromBasket(offerService.get(offerId), basketId);
        return "redirect:/basket";
    }

    @PostMapping("/{basketId}/add")
    public String addProductInBasket(
            @PathVariable("basketId") long basketId,
            @RequestParam("offerId") long offerId
    ) {
        basketService.addOfferInBasket(offerService.get(offerId), basketId);
        return "redirect:/product";
    }

    @ModelAttribute
    public void getBasket(
            Model model,
            @AuthenticationPrincipal User buyer) {
        model.addAttribute("basket", basketService.getBasket(buyer));
    }
}
