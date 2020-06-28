package com.cucumber.service.impl;

import com.cucumber.model.Basket;
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
    public Basket get(Long id) {
        return basketRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        basketRepository.deleteById(id);
    }
}
