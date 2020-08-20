package com.cucumber.service.impl;

import com.cucumber.model.Basket;
import com.cucumber.model.Order;
import com.cucumber.model.Offer;
import com.cucumber.model.State;
import com.cucumber.repository.OrderRepository;
import com.cucumber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean addOrder(Basket basket, String phoneNumber, String address) {
        if (basket != null && !basket.getOffers().isEmpty()) {
            for (Offer offer : basket.getOffers()) {
                Order order = new Order();
                order.setBuyer(basket.getBuyer());
                order.setPhoneNumber(phoneNumber);
                order.setAddress(address);
                order.setState(State.ACTIVE);
                order.setOffer(offer);
                order.setCost(offer.getCost());
                order.setSeller(offer.getSeller());
                orderRepository.save(order);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getAllBuyersOrders(long buyerId) {
        return orderRepository.findByBuyer_Id(buyerId);
    }

    @Override
    public List<Order> getSellerOrders(long sellerId, State state) {
        return orderRepository.findBySeller_IdAndAndState(sellerId, state);
    }

    public void editOrderState(long id, State state) {
        Order order = orderRepository.getOne(id);
        order.setDateOfAction(LocalDate.now());
        order.setState(state);
        orderRepository.save(order);
    }

}
