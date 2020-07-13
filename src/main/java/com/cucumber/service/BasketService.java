package com.cucumber.service;

import com.cucumber.model.Basket;
import com.cucumber.model.Product;
import com.cucumber.model.User;

import java.util.List;

public interface BasketService {
    void save(Basket basket);
    List<Basket> getAll();
    Basket get(long id);
    void delete(long id);
    Basket getBasketByBuyerId(long id);
    void addProductInBasket(Product product, User buyer);
    void deleteProductFromBasket(Product product, User buyer);
}
