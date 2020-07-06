package com.cucumber.service.impl;

import com.cucumber.model.Order;
import com.cucumber.repository.OrderRepository;
import com.cucumber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return orderRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
