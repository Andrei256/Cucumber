package com.cucumber.service.impl;

import com.cucumber.model.Basket;
import com.cucumber.model.Offer;
import com.cucumber.model.User;
import com.cucumber.repository.BasketRepository;
import com.cucumber.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public void save(Basket basket) {
        basketRepository.save(basket);
    }

    @Override
    public List<Basket> getAll() {
        return basketRepository.findAll();
    }

    @Override
    public Basket get(long id) {
        return basketRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        basketRepository.deleteById(id);
    }

    @Override
    public Basket getBasket(User buyer) {
        Basket basket = basketRepository.findByBuyer_Id(buyer.getId());
        if (basket == null) {
            basket = new Basket();
            basket.setBuyer(buyer);
            basketRepository.save(basket);
        }
        return basket;
    }

    @Override
    public void addProductInBasket(Offer offer, long id) {
        Basket basket = basketRepository.findById(id).get();
        List<Offer> offers = basket.getOffers();
        if (!offers.contains(offer)) {
            offers.add(offer);
            basket.setOffers(offers);

            float totalCost = 0;
            for (Offer offerIter : offers) {
                totalCost = totalCost + offerIter.getCost();
            }
            basket.setTotalCost(totalCost);
            basketRepository.save(basket);
        }
    }

    @Override
    public void deleteProductFromBasket(Offer offer, long id) {
        Basket basket = basketRepository.findById(id).get();
        List<Offer> offers = basket.getOffers();
        offers.remove(offer);
        basket.setOffers(offers);

        float totalCost = 0;
        for (Offer offerIter : offers) {
            totalCost = totalCost + offerIter.getCost();
        }
        basket.setTotalCost(totalCost);

        basketRepository.save(basket);
    }


}
