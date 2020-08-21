package com.cucumber.service.impl;

import com.cucumber.model.Review;
import com.cucumber.model.User;
import com.cucumber.repository.ReviewRepository;
import com.cucumber.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

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
        review.setBuyer(buyer);
        review.setSeller(seller);
        reviewRepository.save(review);
    }
}
