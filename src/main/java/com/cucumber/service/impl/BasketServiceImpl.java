package com.cucumber.service.impl;

import com.cucumber.model.Basket;
import com.cucumber.model.Product;
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
    public Basket getBasketByBuyerId(long id) {
        return basketRepository.findByBuyer_Id(id);
    }

    @Override
    public void addProductInBasket(Product product, User buyer) {
        Basket basket = basketRepository.findByBuyer_Id(buyer.getId());
        if (basket == null) {
            basket = new Basket();
            basket.setBuyer(buyer);
        }
        List<Product> products = basket.getProducts();
        if (!products.contains(product)) {
            products.add(product);
            basket.setProducts(products);

            float totalCost = 0;
            for (Product productIter : products) {
                totalCost = totalCost + productIter.getCost();
            }
            basket.setTotalCost(totalCost);

            basketRepository.save(basket);
        }
    }

    @Override
    public void deleteProductFromBasket(Product product, User buyer) {
        Basket basket = basketRepository.findByBuyer_Id(buyer.getId());
        List<Product> products = basket.getProducts();
        products.remove(product);
        basket.setProducts(products);

        float totalCost = 0;
        for (Product productIter : products) {
            totalCost = totalCost + productIter.getCost();
        }
        basket.setTotalCost(totalCost);

        basketRepository.save(basket);
    }


}
