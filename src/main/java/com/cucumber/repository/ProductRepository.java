package com.cucumber.repository;

import com.cucumber.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query (
            value = "select * from product group by product_description_id;",
            nativeQuery = true
    )
    List<Product> getListUniqueProductsFromDB();
}
