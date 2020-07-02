package com.cucumber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @PostMapping("/{id}")
    public String addToBasket(@PathVariable(name = "id") long id, Model model) {
        System.out.println("ок " + id);
        return "redirect";
    }
}
