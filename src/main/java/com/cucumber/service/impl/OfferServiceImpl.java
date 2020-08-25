package com.cucumber.service.impl;

import com.cucumber.model.Offer;
import com.cucumber.model.Product;
import com.cucumber.model.User;
import com.cucumber.repository.OfferRepository;
import com.cucumber.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

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
        return offerRepository.getOne(id);
    }

    @Override
    public void delete(long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public void addOffer(User seller, Offer offer, Product product) {
        Offer offerFromDB = offerRepository.findBySeller_IdAndProduct_Id(seller.getId(), product.getId());
        if (offerFromDB == null) {
            offer.setProduct(product);
            offer.setSeller(seller);
            offerRepository.save(offer);
            log.info("Offer was added: " + offer);
        } else {
            offerFromDB.setCost(offer.getCost());
            offerRepository.save(offerFromDB);
            log.info("Offer was edited: " + offerFromDB);
        }
    }

    @Override
    public List<Offer> getAllOffersBySellerId(long id) {
        return offerRepository.findBySeller_Id(id);
    }

    @Override
    public Offer getBySellerIdAndProductId(long sellerId, long productId) {
        Offer offer = offerRepository.findBySeller_IdAndProduct_Id(sellerId, productId);
        if (offer == null) {
            return new Offer();
        }
        return offer;
    }

}
