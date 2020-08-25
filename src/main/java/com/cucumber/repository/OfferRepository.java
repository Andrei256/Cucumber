package com.cucumber.repository;

import com.cucumber.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer findBySeller_IdAndProduct_Id(long sellerId, long productId);

    List<Offer> findBySeller_Id(long id);
}
