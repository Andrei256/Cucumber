package com.cucumber.service;

import com.cucumber.model.Offer;
import com.cucumber.model.Product;
import com.cucumber.model.User;

import java.util.List;

public interface OfferService {

    void save(Offer offer);

    List<Offer> getAll();

    Offer get(long id);

    void delete(long id);

    void addOffer(User seller, Offer offer, Product product);

    List<Offer> getAllOffersBySellerId(long id);

    Offer getBySellerIdAndProductId(long sellerId, long productId);
}
