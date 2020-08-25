package com.cucumber.service;

import com.cucumber.model.Basket;
import com.cucumber.model.Offer;
import com.cucumber.model.User;

import java.util.List;

public interface BasketService {

    void save(Basket basket);

    List<Basket> getAll();

    Basket get(long id);

    void delete(long id);

    Basket getBasket(User buyer);

    void addOfferInBasket(Offer offer, long id);

    void deleteOfferFromBasket(Offer offer, long id);
}
