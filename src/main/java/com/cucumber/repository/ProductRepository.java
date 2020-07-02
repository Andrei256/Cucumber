package com.cucumber.repository;

import com.cucumber.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    float
}
