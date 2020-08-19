package com.cucumber.repository;

import com.cucumber.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByModelName(String modelName);
    List<Product> findByActiveIsTrue();
    List<Product> findByActiveIsFalse();
}
