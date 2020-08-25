package com.cucumber.service.impl;

import com.cucumber.model.Review;
import com.cucumber.model.User;
import com.cucumber.repository.ReviewRepository;
import com.cucumber.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review get(long id) {
        return reviewRepository.getOne(id);
    }

    @Override
    public void delete(long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void addReview(User buyer, User seller, Review review) {
        Review reviewFromDB = reviewRepository.findByBuyer_IdAndSeller_Id(buyer.getId(), seller.getId());
        if (reviewFromDB != null) {
            review.setId(reviewFromDB.getId());
        }
        review.setBuyer(buyer);
        review.setSeller(seller);
        reviewRepository.save(review);
        log.info("Review was added: " + review);
    }
}
