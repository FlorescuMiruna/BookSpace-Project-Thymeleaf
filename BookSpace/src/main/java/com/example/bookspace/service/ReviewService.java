package com.example.bookspace.service;


import com.example.bookspace.model.Review;
import com.example.bookspace.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getAllReviewsByBookId(Long bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }

    public Review getReviewById(Long id) {
        Optional<Review> optional = reviewRepository.findById(id);
        Review review = null;
        if (optional.isPresent())
            review = optional.get();
        else
            throw new RuntimeException(
                    "Review not found for id : " + id);
        return review;
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }
}
