package com.cucumber.service.impl;

import com.cucumber.model.Offer;
import com.cucumber.model.Product;
import com.cucumber.model.User;
import com.cucumber.repository.OfferRepository;
import com.cucumber.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public Offer get(long id) {
        return offerRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public void addOffer(User seller, float cost, Product product) {
        Offer offer = offerRepository.findBySeller_IdAndProduct_Id(seller.getId(), product.getId());
        if (offer == null) {
            offer = new Offer();
            offer.setCost(cost);
            offer.setProduct(product);
            offer.setSeller(seller);
        } else {
            offer.setCost(cost);
        }
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> getAllOffersBySellerId(long id) {
        return offerRepository.findBySeller_Id(id);
    }

}
