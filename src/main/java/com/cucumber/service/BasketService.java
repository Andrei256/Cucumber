package com.cucumber.service;

import com.cucumber.model.Basket;

import java.util.List;

public interface BasketService {
    void save(Basket basket);
    List<Basket> getAll();
    Basket get(long id);
    void delete(long id);
    Basket getBasketByBuyerId(long id);
}
