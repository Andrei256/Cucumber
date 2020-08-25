package com.cucumber.service;

import com.cucumber.model.Review;
import com.cucumber.model.User;

import java.util.List;

public interface ReviewService {

    void save(Review review);

    List<Review> getAll();

    Review get(long id);

    void delete(long id);

    void addReview(User buyer, User seller, Review review);
}
