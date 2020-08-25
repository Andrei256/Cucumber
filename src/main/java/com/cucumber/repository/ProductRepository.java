package com.cucumber.repository;

import com.cucumber.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByModelName(String modelName);

    List<Product> findByActiveIsTrue();

    List<Product> findByActiveIsFalse();

    @Query(value = "SELECT p FROM Product p WHERE p.modelName LIKE '%' || :keyword || '%'"
            + " OR p.description LIKE '%' || :keyword || '%'")
    List<Product> search(@Param("keyword") String keyword);
}
