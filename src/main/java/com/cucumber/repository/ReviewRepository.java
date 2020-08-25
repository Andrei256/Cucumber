package com.cucumber.repository;

import com.cucumber.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByBuyer_IdAndSeller_Id(long buyerId, long sellerId);
}
