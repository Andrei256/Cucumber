package com.cucumber.service;

import com.cucumber.model.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);
    List<Order> getAll();
    Order get(Long id);
    void delete(Long id);
}
