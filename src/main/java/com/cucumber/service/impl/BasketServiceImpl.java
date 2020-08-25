package com.cucumber.service.impl;

import com.cucumber.model.Basket;
import com.cucumber.model.Offer;
import com.cucumber.model.User;
import com.cucumber.repository.BasketRepository;
import com.cucumber.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class BasketServiceImpl implements BasketService {

    private BasketRepository basketRepository;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

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
        return basketRepository.getOne(id);
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
    public void addOfferInBasket(Offer offer, long id) {
        Basket basket = basketRepository.getOne(id);
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
            log.info("Offer was added in basket: " + offer);
        }
    }

    @Override
    public void deleteOfferFromBasket(Offer offer, long id) {
        Basket basket = basketRepository.getOne(id);
        List<Offer> offers = basket.getOffers();
        offers.remove(offer);
        basket.setOffers(offers);
        float totalCost = 0;
        for (Offer offerIter : offers) {
            totalCost = totalCost + offerIter.getCost();
        }
        basket.setTotalCost(totalCost);
        basketRepository.save(basket);
        log.info("Offer was deleted from basket: " + offer);
    }

}
