package com.cucumber.repository;

import com.cucumber.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Basket findByBuyer_Id(long id);
}
