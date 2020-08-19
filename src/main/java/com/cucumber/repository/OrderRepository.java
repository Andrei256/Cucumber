package com.cucumber.repository;

import com.cucumber.model.Order;
import com.cucumber.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer_Id(long buyerId);
    List<Order> findBySeller_IdAndAndState(long sellerId, State state);
}
