package com.cucumber.service;

import com.cucumber.model.Basket;
import com.cucumber.model.Order;
import com.cucumber.model.State;

import java.util.List;

public interface OrderService {

    void save(Order order);

    List<Order> getAll();

    Order get(long id);

    void delete(long id);

    void addOrder(Basket basket, Order order);

    List<Order> getAllBuyersOrders(long buyerId);

    List<Order> getSellerOrders(long sellerId, State state);

    void editOrderState(long id, State state);
}
