package com.cucumber.repository;

import com.cucumber.model.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}
