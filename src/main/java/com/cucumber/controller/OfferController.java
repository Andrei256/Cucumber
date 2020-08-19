package com.cucumber.controller;

import com.cucumber.model.User;
import com.cucumber.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    OfferService offerService;

    @GetMapping("/all")
    public String showAllShopOffers(
            @AuthenticationPrincipal User seller,
            Model model) {
        model.addAttribute("offers", offerService.getAllOffersBySellerId(seller.getId()));
        return "shop/offers";
    }

    @PostMapping("/{offerId}/delete")
    public String deleteProductOffer(@PathVariable(name = "offerId") long offerId) {
        offerService.delete(offerId);
        return "redirect:/offer/all";
    }
}
